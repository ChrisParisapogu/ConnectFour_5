package com.example.connectfour;

import static com.example.connectfour.BoardFragment.isPlayer1Turn;
import static com.example.connectfour.BoardFragment.updateStatus;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

public class GameView extends View {
    private Paint boardPaint;
    private Paint cellPaint;
    private Paint player1Paint;
    private Paint player2Paint;
    private int[][] board;
    private static final int EMPTY = 0;
    private static final int PLAYER1 = 1;
    private static final int PLAYER2 = 2;
    private static final int ROWS = 7;
    private static final int COLS = 6;
    private float cellSize;
    private float xOffset;
    private float yOffset;

    public GameView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        boardPaint = new Paint();
        boardPaint.setColor(Color.parseColor("#FFEB3B"));
        boardPaint.setStyle(Paint.Style.FILL);

        cellPaint = new Paint();
        cellPaint.setColor(Color.WHITE);
        cellPaint.setStyle(Paint.Style.FILL);

        player1Paint = new Paint();
        player1Paint.setColor(Color.RED);
        player1Paint.setStyle(Paint.Style.FILL);

        player2Paint = new Paint();
        player2Paint.setColor(Color.BLUE);
        player2Paint.setStyle(Paint.Style.FILL);

        board = new int[ROWS][COLS];
        resetGame();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        // Draw the blue background board with padding
        float boardWidth = COLS * cellSize;
        float boardHeight = ROWS * cellSize;
        canvas.drawRect(xOffset, yOffset, xOffset + boardWidth, yOffset + boardHeight, boardPaint);

        // Draw the circles with proper spacing
        float radius = (cellSize * 0.4f);  // Circle radius
        for (int i = 0; i < ROWS; i++) {
            for (int j = 0; j < COLS; j++) {
                float centerX = xOffset + (j * cellSize) + (cellSize / 2);
                float centerY = yOffset + (i * cellSize) + (cellSize / 2);

                Paint paint;
                switch (board[i][j]) {
                    case PLAYER1:
                        paint = player1Paint;
                        break;
                    case PLAYER2:
                        paint = player2Paint;
                        break;
                    default:
                        paint = cellPaint;
                        break;
                }

                canvas.drawCircle(centerX, centerY, radius, paint);
            }
        }
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        // Add padding to ensure full circle visibility
        int padding = 50; // Padding in pixels

        // Calculate the available space considering padding
        int availableWidth = getMeasuredWidth() - ( padding);
        int availableHeight = getMeasuredHeight() - (2 * padding);

        // Calculate cell size based on available space and grid dimensions
        cellSize = Math.min(availableWidth / COLS, availableHeight / ROWS);

        // Calculate offsets to center the board
        xOffset = (getMeasuredWidth() - (COLS * cellSize)) / 2;
        yOffset = (getMeasuredHeight() - (ROWS * cellSize)) / 2;

        // Ensure minimum spacing from edges
        xOffset = Math.max(xOffset, padding);
        yOffset = Math.max(yOffset, padding);

        // Set the final measured dimensions
        int finalSize = (int) (Math.max(COLS * cellSize, ROWS * cellSize) + (2 * padding));
        setMeasuredDimension(finalSize, finalSize);
    }

    public void resetGame() {
        for (int i = 0; i < ROWS; i++) {
            for (int j = 0; j < COLS; j++) {
                board[i][j] = EMPTY;
            }
        }
        invalidate();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            float x = event.getX();
            int col = (int)((x - xOffset) / cellSize);

            if (col >= 0 && col < COLS) {
                dropPiece(col);
                return true;
            }
        }
        return false;
    }

    private void dropPiece(int col) {
        // Find the lowest empty row in the selected column
        for (int row = ROWS - 1; row >= 0; row--) {
            if (board[row][col] == EMPTY) {
                board[row][col] = isPlayer1Turn ? PLAYER1 : PLAYER2;
                isPlayer1Turn = !isPlayer1Turn;
                invalidate();

                if (checkWin(row, col)) {
                    showWinDialog();
                } else if (isBoardFull()) {
                    showDrawDialog();
                }

                updateStatus();
                break;
            }
        }
    }

    private boolean checkWin(int row, int col) {
        int player = board[row][col];

        // Check horizontal
        int count = 0;
        for (int j = 0; j < COLS; j++) {
            count = (board[row][j] == player) ? count + 1 : 0;
            if (count >= 4) return true;
        }

        // Check vertical
        count = 0;
        for (int i = 0; i < ROWS; i++) {
            count = (board[i][col] == player) ? count + 1 : 0;
            if (count >= 4) return true;
        }

        // Check diagonal (top-left to bottom-right)
        count = 0;
        for (int i = -3; i <= 3; i++) {
            int r = row + i;
            int c = col + i;
            if (r >= 0 && r < ROWS && c >= 0 && c < COLS) {
                count = (board[r][c] == player) ? count + 1 : 0;
                if (count >= 4) return true;
            }
        }

        // Check diagonal (top-right to bottom-left)
        count = 0;
        for (int i = -3; i <= 3; i++) {
            int r = row + i;
            int c = col - i;
            if (r >= 0 && r < ROWS && c >= 0 && c < COLS) {
                count = (board[r][c] == player) ? count + 1 : 0;
                if (count >= 4) return true;
            }
        }

        return false;
    }

    private boolean isBoardFull() {
        for (int i = 0; i < ROWS; i++) {
            for (int j = 0; j < COLS; j++) {
                if (board[i][j] == EMPTY) return false;
            }
        }
        return true;
    }

    private void showWinDialog() {
        String winner = !isPlayer1Turn ? "Player 1" : "Player 2";
        Toast.makeText(getContext(),winner + " wins!",Toast.LENGTH_LONG).show();
        resetGame();
//        new AlertDialog.Builder(getContext())
//                .setTitle("Game Over")
//                .setMessage(winner + " wins!")
//                .setPositiveButton("Play Again", (dialog, which) -> resetGame())
//                .setCancelable(false)
//                .show();
    }

    private void showDrawDialog() {
        Toast.makeText(getContext(),"It's a draw!",Toast.LENGTH_LONG).show();
        resetGame();
//        new AlertDialog.Builder(getContext())
//                .setTitle("Game Over")
//                .setMessage("It's a draw!")
//                .setPositiveButton("Play Again", (dialog, which) -> resetGame())
//                .setCancelable(false)
//                .show();
    }
}
