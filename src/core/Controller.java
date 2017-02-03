package core;

import java.io.File;

/*
 * Update log:
 * 2017-02-03:
 *      Created by Zhang Yufei. 
 */

/**
 * The Controller class for communication between gui and core classes.
 * 
 * @author Zhang Yufei.
 * @version 2017-02-03
 *
 */
public class Controller {
    /**
     * Set the parameters used in this program.
     * 
     * @param srcPath
     *            The store position of source picture.
     * @param photoPath
     *            The directory which contains the photos used for puzzle.
     * @param resultPath
     *            The store position for result file.
     * @param width
     *            The width of the element photo.
     * @param height
     *            The height of the element photo.
     * @param l
     *            The multiplying times.
     */
    public static void setParameter(String srcPath, String photoPath,
            String resultPath, int width, int height, int l) {
        Parameter.init(srcPath, photoPath, resultPath, width, height, l);
    }

    /**
     * Start generating the photo puzzle.
     */
    public static void startup() {
        Ratio.changeRatio();
        Size.changeSize();
        Sample.getSample();
        Puzzle.combine();
    }
    
    /**
     * Delete the middle file.
     */
    public static void delete() {
        delete(new File(Parameter.ratioPath));
        delete(new File(Parameter.sizePath));
    }
    
    /**
     * Delete the middle file using iterator
     */
    private static void delete(File file) {
        if(file.isFile()) {
            file.delete();
        } else {
            File[] files = file.listFiles();
            
            for(File f : files) {
                delete(f);
            }
            
            file.delete();
        }
    }
}
