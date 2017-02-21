import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;

class ImageData {

    class RecognizedCircle {
        Point center;
        int radius;
        ArrayList<Point> checkedPoints;

        RecognizedCircle(Point center, int radius, ArrayList<Point> checkedPoints) {
            this.center = center;
            this.radius = radius;
            this.checkedPoints = checkedPoints;
        }
    }

    int[][] points;
    ArrayList<Point> coloredPoints = new ArrayList<Point>();

    static ImageData fromFile(String pathToFile) {
        try {
            ImageData result = new ImageData();
            File imageFile = new File(pathToFile);
            BufferedImage image = ImageIO.read(imageFile);
            int imageWidth = image.getWidth();
            int imageHeight = image.getHeight();
            result.points = new int[imageWidth][imageHeight];

            for (int x = 0; x < imageHeight; x++) {
                for (int y = 0; y < imageWidth; y++) {
                    int color = image.getRGB(x, y);
                    if (color != Color.WHITE.getRGB()) {
                        result.points[x][y] = 1;
                        result.coloredPoints.add(new Point(x, y));
                    } else {
                        result.points[x][y] = 0;
                    }
                }
            }

            return result;
        } catch (Exception ex) {
            return null;
        }
    }

    static ImageData fromScratch(int width, int height) {
        ImageData result = new ImageData();
        result.points = new int[1000][1000];
        return result;
    }


    RecognizedCircle recognizeCircle() {

        boolean hasRecognized;
        int possibleDiameter;
        Point possibleCenter;

        for (int i = 0; i < coloredPoints.size() / 2; i++) {
            for (int j = coloredPoints.size() - 1; j > coloredPoints.size() / 2 + 1; j--) {
                Point first = coloredPoints.get(i);
                Point last = coloredPoints.get(j);

                possibleDiameter = distance(first, last);

                possibleCenter = new Point();
                possibleCenter.x = (first.x + last.x) / 2;
                possibleCenter.y = (first.y + last.y) / 2;

                hasRecognized = checkPossibleCircle(possibleCenter, first);

                if (hasRecognized) return new RecognizedCircle(possibleCenter, possibleDiameter / 2, lastCheckedPointsOnCircle);
            }
        }

        return null;
    }

    /////////////////////////////////////////////////////////////////////////////////
    // Yep, I know, dummy way to write code, but I'm too lazy to rewrite It.
    // I just need to get fucking mark tomorrow, and I don't care how it is written.
    ArrayList<Point> lastCheckedPointsOnCircle;
    /////////////////////////////////////////////////////////////////////////////////

    boolean checkPossibleCircle(Point possibleCenter, Point pointOnCircle) {
        ArrayList<Point> pointsToCheck = new ArrayList<Point>();
        pointsToCheck.add(pointOnCircle);

        int x0 = possibleCenter.x;
        int y0 = possibleCenter.y;
        for (int i = 0; i < 7; i++) {
            Point lastAdded = pointsToCheck.get(pointsToCheck.size() - 1);
            int x = lastAdded.x;
            int y = lastAdded.y;
            double sincos45 = Math.sqrt(2) / 2;

            int xn = (int) (-sincos45 * (y - y0) + sincos45 * (x - x0) + x0);
            int yn = (int) (sincos45 * (y - y0) + sincos45 * (x - x0) + y0);

            pointsToCheck.add(new Point(xn, yn));
        }

        lastCheckedPointsOnCircle = pointsToCheck;

        for (Point pointToCheck :
                pointsToCheck) {
            if (!checkPossiblePointOnCircle(pointToCheck)) return false;
        }
        return true;
    }

    private boolean checkPossiblePointOnCircle(Point pointToCheck) {
        int x = pointToCheck.x;
        int y = pointToCheck.y;

        int coloredPointsCount = 0;
        int accuracyX = (int) (points.length    * 0.001);
        int accuracyY = (int) (points[0].length * 0.001);

        for (int deltaX = -accuracyX; deltaX <= accuracyX; deltaX++) {
            for (int deltaY = -accuracyY; deltaY <= accuracyY; deltaY++) {
                try {
                    coloredPointsCount += points[x + deltaX][y + deltaY];
                } catch (ArrayIndexOutOfBoundsException ignored) { }
            }
        }
        return coloredPointsCount > 0;
    }

    static private int distance(Point from, Point to) {
        return (int) Math.sqrt((to.x - from.x) * (to.x - from.x) + (to.y - from.y) * (to.y - from.y));
    }
}