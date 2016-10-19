package com.novoda.canvas.faceoff;

enum XSide {
    LEFT {
        @Override
        int getCentreOfSide(int totalWidth) {
            return totalWidth / 4;
        }
    },
    RIGHT {
        @Override
        int getCentreOfSide(int totalWidth) {
            return 3 * totalWidth / 4;
        }
    };

    abstract int getCentreOfSide(int totalWidth);
}
