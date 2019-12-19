/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package helpers;

import com.itextpdf.io.font.constants.StandardFonts;
import com.itextpdf.kernel.colors.Color;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.layout.property.TextAlignment;
import java.io.IOException;

/**
 *
 * @author David
 */
public class FontOptions {
    private PdfFont font;
    private Color fontColor;
    private float fontSize;
    private TextAlignment textAlignment;

    public FontOptions (String font, Color fontColor, float fontSize, TextAlignment textAlignment) throws IOException {
        this.font = PdfFontFactory.createFont(font);
        this.fontColor = fontColor;
        this.fontSize = fontSize;
        this.textAlignment = textAlignment;
    }

    public PdfFont getFont() {
        return font;
    }

    public void setFont(PdfFont font) {
        this.font = font;
    }

    public Color getFontColor() {
        return fontColor;
    }

    public void setFontColor(Color fontColor) {
        this.fontColor = fontColor;
    }

    public float getFontSize() {
        return fontSize;
    }

    public void setFontSize(float fontSize) {
        this.fontSize = fontSize;
    }

    public TextAlignment getTextAlignment() {
        return textAlignment;
    }

    public void setTextAlignment(TextAlignment textAlignment) {
        this.textAlignment = textAlignment;
    }
}
