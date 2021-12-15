/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ai.lerna.flapi.entity;

import java.math.BigDecimal;

/**
 *
 * @author gkellaris
 */
public class LernaMLParameters {
    private BigDecimal normalization;   //Normalization value alpha or lambda to avoid overfitting (0.05, 0.1, 0.5, 1.0, 5.0 etc.)
    private int iterations;             //Number of local iterations
    //private BigDecimal thres;         //Threshold value for the termination/convergence of SGD, usually 0.0001
    private BigDecimal lr;              //The learning rate, usually 0.05
    private int dimensions;                   //The number of features/weights
    private BigDecimal data_split;           //The percentage of test/train data
    
    public BigDecimal getNormalization() {
        return normalization;
    }

    public void setNormalization(BigDecimal normalization) {
        this.normalization = normalization;
    }
    
    public int getIterations() {
        return iterations;
    }

    public void setIterations(int iterations) {
        this.iterations = iterations;
    }
    
//    public BigDecimal getThres() {
//        return thres;
//    }
//
//    public void setThres(BigDecimal thres) {
//        this.thres = thres;
//    }
    
    public BigDecimal getLR() {
        return lr;
    }

    public void setLR(BigDecimal lr) {
        this.lr = lr;
    }
    
    public int getDimensions() {
        return dimensions;
    }

    public void setDimensions(int dimensions) {
        this.dimensions = dimensions;
    }
    
    public BigDecimal getDataSplit() {
        return data_split;
    }

    public void setDataSplit(BigDecimal data_split) {
        this.data_split = data_split;
    }
    
}
