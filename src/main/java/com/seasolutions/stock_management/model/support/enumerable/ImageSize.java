package com.seasolutions.stock_management.model.support.enumerable;

public enum ImageSize {
    small(200, 200, 0.99f),
    big(300, 300, 0.99f),
    original(600, 600, 0.9f),
    headerBackground(2500, 600, 0.5f),
    headerBackgroundTablet(780, 187, 0.5f),
    headerLogo(480, 480, 0.9f),
    headerLogoTablet(270, 270, 0.8f),
    basicInformation(320,  285, 0.9f),
    positionInformation(320,  285, 0.9f);

    public int width;
    public int height;
    public float compressionQuality;

    ImageSize(final int width, final int height, float compressionQuality) {
        this.width = width;
        this.height = height;
        this.compressionQuality = compressionQuality;
    }
}
