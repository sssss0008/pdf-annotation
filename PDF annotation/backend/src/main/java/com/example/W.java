package com.example;

public class W {
    
}
package com.example;

import java.util.List;

public class W {
    private String originalPdf;
    private List<D> annotations;

    public static class D {
        private String text;
        private int page;
        private float x;
        private float y;
        private float width;
        private float height;
        private String color;
        private String link;

        public String getText() { return text; }
        public void setText(String text) { this.text = text; }
        public int getPage() { return page; }
        public void setPage(int page) { this.page = page; }
        public float getX() { return x; }
        public void setX(float x) { this.x = x; }
        public float getY() { return y; }
        public void setY(float y) { this.y = y; }
        public float getWidth() { return width; }
        public void setWidth(float width) { this.width = width; }
        public float getHeight() { return height; }
        public void setHeight(float height) { this.height = height; }
        public String getColor() { return color; }
        public void setColor(String color) { this.color = color; }
        public String getLink() { return link; }
        public void setLink(String link) { this.link = link; }
    }

    public String getOriginalPdf() { return originalPdf; }
    public void setOriginalPdf(String originalPdf) { this.originalPdf = originalPdf; }
    public List<D> getAnnotations() { return annotations; }
    public void setAnnotations(List<D> annotations) { this.annotations = annotations; }
}