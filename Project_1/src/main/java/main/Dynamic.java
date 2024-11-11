package main;


public class Dynamic {
    private int[][] dpTable;
    private int[] turns;

    public Dynamic(int[] arr) {
        this.dpTable = calculateDpTable(arr);
        this.turns = calculateTurns(this.dpTable, arr);
    }

    public int[][] calculateDpTable(int[] piles) {
        int n = piles.length;
        int[][] dp = new int[n][n];

        // Initialize base cases
        for (int i = 0; i < n; i++) {
            dp[i][i] = piles[i];
        }

        // Fill DP table
        for (int len = 2; len <= n; len++) {
            for (int i = 0; i <= n - len; i++) {
                int j = i + len - 1;
                int pickLeft = piles[i] - dp[i + 1][j];
                int pickRight = piles[j] - dp[i][j - 1];
                dp[i][j] = Math.max(pickLeft, pickRight);
            }
        }
        return dp;
    }

    public int[] calculateTurns(int[][] dp, int[] piles) {
        int n = piles.length;
        int[] moves = new int[n];
        int i = 0, j = n - 1;
        int aliceIndex = 0;
        int bobIndex = n - 1;
        boolean aliceTurn = true;

        while (i <= j) {
            int leftOption = (i + 1 <= j) ? dp[i + 1][j] : 0;  // Check if i+1 is within bounds
            int rightOption = (i <= j - 1) ? dp[i][j - 1] : 0;  // Check if j-1 is within bounds

            if (aliceTurn) {
                if (piles[i] - leftOption >= piles[j] - rightOption) {
                    moves[aliceIndex++] = piles[i];
                    i++;
                } else {
                    moves[aliceIndex++] = piles[j];
                    j--;
                }
            } else {
                if (piles[i] - leftOption >= piles[j] - rightOption) {
                    moves[bobIndex--] = piles[i];
                    i++;
                } else {
                    moves[bobIndex--] = piles[j];
                    j--;
                }
            }
            aliceTurn = !aliceTurn;  // Switch turns
        }

        return moves;
    }

    public int[][] getDpTable() {
        return dpTable;
    }

    public int[] getTurns() {
        return turns;
    }
}
