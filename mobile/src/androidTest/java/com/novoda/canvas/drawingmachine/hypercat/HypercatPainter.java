package com.novoda.canvas.drawingmachine.hypercat;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.NonNull;

import java.util.List;
import java.util.Random;

class HypercatPainter {

    private final Paint paint, pointPaint;
    private double[] yValues;
    private double[] xValues;
    private List<GraphEdge> graphEdges;
    private Voronoi voronoi;
    private int graphEdgeCounter = 0;
    private final Random random;

    public static HypercatPainter newInstance() {
        return new HypercatPainter();
    }

    private HypercatPainter() {
        paint = new Paint();
        pointPaint = new Paint();
        paint.setColor(Color.parseColor("#0FACE0"));
        pointPaint.setColor(Color.parseColor("#EA9530"));
        paint.setStrokeWidth(10);

        voronoi = new Voronoi(100d);
        random = new Random();
        xValues = new double[35];
        yValues = new double[35];
    }

    void releaseTheHypercat(@NonNull Canvas canvas) {
        if (graphEdges == null) {
            for (int i = 0; i < xValues.length; i++) {
                xValues[i] = random.nextInt(canvas.getWidth());
            }

            for (int k = 0; k < yValues.length; k++) {
                yValues[k] = random.nextInt(canvas.getHeight());
            }
            graphEdges = voronoi.generateVoronoi(xValues, yValues, 0, canvas.getWidth(), 0, canvas.getHeight());
            for (int j = 0; j < xValues.length; j++) {
                canvas.drawCircle((float) xValues[j], (float) yValues[j], 20, pointPaint);
            }
        }

        GraphEdge edge = graphEdges.get(graphEdgeCounter);
        canvas.drawLine((float) edge.x1, (float) edge.y1, (float) edge.x2, (float) edge.y2, paint);
        if (graphEdgeCounter < graphEdges.size()) {
            graphEdgeCounter += 1;
            try {
                Thread.sleep(100);
            } catch (Exception e) {

            }

        }
    }

}
