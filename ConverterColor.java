import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import static java.lang.Math.max;
import static java.lang.Math.min;

public class ConverterColor extends JFrame {
    private JTabbedPane tabbedPane;
    private JPanel colorPanel;
    private JButton rgbButton, hlsButton, cmykButton;

    // RGB components
    private JTextField rField, gField, bField;
    private JSlider redSlider, greenSlider, blueSlider;
    // HLS components
    private JTextField hField, lField, sField;

    // CMYK components
    private JTextField cField, mField, yField, kField;

    public ConverterColor() {
        setTitle("Color Converter");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        tabbedPane = new JTabbedPane();

        colorPanel = new JPanel();
        colorPanel.setPreferredSize(new Dimension(400, 200));
        add(colorPanel, BorderLayout.NORTH);

        tabbedPane.addTab("RGB", createRGBPanel());
        tabbedPane.addTab("HLS", createHLSPanel());
        tabbedPane.addTab("CMYK", createCMYKPanel());

        add(tabbedPane, BorderLayout.CENTER);
        setVisible(true);
    }

    private JPanel createRGBPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(4, 2));

        /*redSlider = createColorSlider(0, 255);
        greenSlider = createColorSlider(0, 255);
        blueSlider = createColorSlider(0, 255);*/

        panel.add(new JLabel("R:"));
        rField = new JTextField("255");
        panel.add(rField);

        panel.add(new JLabel("G:"));
        gField = new JTextField("255");
        panel.add(gField);

        panel.add(new JLabel("B:"));
        bField = new JTextField("255");
        panel.add(bField);
        rgbButton= new JButton("Update Color");
        rgbButton.addActionListener(e -> updateColorFromRGB());
        panel.add(rgbButton);

        return panel;
    }

    /*private JSlider createColorSlider(int min, int max) {
        JSlider slider = new JSlider(min, max);
        slider.addChangeListener(e -> updateColor());
        return slider;
    }
    private void updateColor() {
        int r = redSlider.getValue();
        int g = greenSlider.getValue();
        int b = blueSlider.getValue();

        colorPanel.setBackground(new Color(r, g, b));
        RGBtoCMYK(r, g, b);
        updateHLSValues(r, g, b);

        redField.setText(String.valueOf(r));
        greenField.setText(String.valueOf(g));
        blueField.setText(String.valueOf(b));
    }*/

    private JPanel createHLSPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(4, 2));

        panel.add(new JLabel("Hue:"));
        hField = new JTextField("360");
        panel.add(hField);

        panel.add(new JLabel("Lightness:"));
        lField = new JTextField("100");
        panel.add(lField);

        panel.add(new JLabel("Saturation:"));
        sField = new JTextField("100");
        panel.add(sField);

        hlsButton = new JButton("Update Color");
        hlsButton.addActionListener(e -> updateColorFromHLS());
        panel.add(hlsButton);

        return panel;
    }

    private JPanel createCMYKPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(5, 2));

        panel.add(new JLabel("Cyan:"));
        cField = new JTextField("0");
        panel.add(cField);

        panel.add(new JLabel("Magenta:"));
        mField = new JTextField("0");
        panel.add(mField);

        panel.add(new JLabel("Yellow:"));
        yField = new JTextField("0");
        panel.add(yField);

        panel.add(new JLabel("Key:"));
        kField = new JTextField("0");
        panel.add(kField);

        cmykButton = new JButton("Update Color");
        cmykButton.addActionListener(e -> updateColorFromCMYK());
        panel.add(cmykButton);

        return panel;
    }

    private void updateColorFromRGB() {
        int r = Integer.parseInt(rField.getText());
        int g = Integer.parseInt(gField.getText());
        int b = Integer.parseInt(bField.getText());
        Color color = new Color(r, g, b);
        updateColor(color);
        updateCMYK(color);
        updateHLS(color);
    }

    private void updateColorFromHLS() {
        float h = Float.parseFloat(hField.getText());
        float s = Float.parseFloat(lField.getText());
        float l = Float.parseFloat(sField.getText());

        int [] rgb =HLStoRGB(h,s,l);
        Color color = new Color(rgb[0], rgb[1], rgb[2]);
        updateColor(color);
        updateCMYK(color);
        updateRGB(color);
    }

    private void updateColorFromCMYK() {
        float c = Float.parseFloat(cField.getText());
        float m = Float.parseFloat(mField.getText());
        float y = Float.parseFloat(yField.getText());
        float k = Float.parseFloat(kField.getText());

        float[] rgb =CMYK_to_RGB(c,m,y,k);
        Color color = new Color((int)rgb[0], (int)rgb[1],(int) rgb[2]);
        updateColor(color);
        updateHLS(color);
        updateRGB(color);
    }

    private void updateColor(Color color) {
        colorPanel.setBackground(color);
    }

    private void updateCMYK(Color color) {
        float r = color.getRed();
        float g = color.getGreen();
        float b = color.getBlue();

        float[] cmyk =RGB_to_CMYK(r,g,b);
        //Color color = new Color(cmyk[0], cmyk[1],cmyk[2],cmyk[3]);
        cField.setText(String.format("%.0f", cmyk[0]*100));
        mField.setText(String.format("%.0f", cmyk[1]*100));
        yField.setText(String.format("%.0f", cmyk[2]*100));
        kField.setText(String.format("%.0f", cmyk[3]*100));

    }

    private void updateHLS(Color color) {
        float r = color.getRed();
        float g = color.getGreen();
        float b = color.getBlue();
        // Convert to HLS
        float[] hls = RGBtoHLS(r, g, b);
        hField.setText(String.format("%.0f", hls[0] * 60));
        sField.setText(String.format("%.0f", hls[1] * 100));
        lField.setText(String.format("%.0f", hls[2] * 100));
    }
    public static int[] HLStoRGB(float h, float s, float l) {
        h=h/360;
        s=s/100;
        l=l/100;
        if (s == 0) {
            return new int[] { (int) (l * 255), (int) (l * 255), (int) (l * 255) }; // achromatic
        } else {
            float q = l < 0.5f ? l * (1 + s) : (l + s) - (s * l);
            float p = 2 * l - q;
            float[] rgb = new float[3];
            for (int i = 0; i < 3; i++) {
                float hue = h + (1f / 3) * (i - 1);
                if (hue < 0) {
                    hue += 1;
                } else if (hue > 1) {
                    hue -= 1;
                }
                if (6 * hue < 1) {
                    rgb[i] = p + ((q - p) * 6 *hue);
                } else if (2 * hue < 1) {
                    rgb[i] = q;
                } else if (3 * hue < 2) {
                    rgb[i] = p + ((q - p) *6 * (2 / 3 - hue));
                } else {
                    rgb[i] = p;
                }
            }
            return new int[] { (int) (rgb[0] * 255), (int) (rgb[1] * 255), (int) (rgb[2] * 255) };
        }
    }

/*    public static float[] HLS_to_RGB(float H, float L, float S) {
        L=L/100;
        S=S/100;
        if (S == 0) {
            float color = (int) (L*255);
            return new float[]{color, color, color};
        }
        float tmp_1 = L >= 0.5 ? L + S - L * S : L * (1 + S);
        float tmp_2 = 2 * L - tmp_1;
        float tmp_R = (float) (H + 0.33);
        float tmp_G = H;
        float tmp_B = (float) (H - 0.33);

        // все пренадлежат [0,1]
        tmp_R = tmp_R > 1 ? tmp_R - 1: tmp_R;
        tmp_R = tmp_R < 0 ? tmp_R + 1 : tmp_R;
        tmp_G = tmp_G > 1 ? tmp_G - 1 : tmp_G;
        tmp_G = tmp_G < 0 ? tmp_G + 1 : tmp_G;
        tmp_B = tmp_B > 1 ? tmp_B - 1 : tmp_B;
        tmp_B = tmp_B < 0 ? tmp_B + 1 : tmp_B;
        float R,G,B;
        if(6* tmp_R < 1){
            R = tmp_2 + (tmp_1 - tmp_2)*6*tmp_R;
        } else if (2 * tmp_R < 1) {
            R = tmp_1;
        } else if (3 * tmp_R < 2) {
            R = tmp_2 + (tmp_1 - tmp_2)*((float) 2 / 3 +  - tmp_R*6);

        } else {
            R = tmp_R;
        }

        if(6* tmp_G < 1){
            G = tmp_2 + (tmp_1 - tmp_2)*6*tmp_G;
        } else if (2 * tmp_G < 1) {
            G = tmp_1;
        } else if (3 * tmp_G < 2) {
            G = tmp_2 + (tmp_1 - tmp_2)*((float) 2 / 3 +  - tmp_G*6);

        } else {
            G = tmp_G;
        }

        if(6* tmp_B < 1){
            B = tmp_2 + (tmp_1 - tmp_2)*6*tmp_B;
        } else if (2 * tmp_B < 1) {
            B = tmp_1;
        } else if (3 * tmp_B < 2) {
            B = tmp_2 + (tmp_1 - tmp_2)*((float) 2 / 3 +  - tmp_B*6);

        } else {
            B = tmp_R;
        }
        return new float[]{R*255, G*255, B*255};
    }*/


    public  static float[] RGBtoHLS(float red, float green, float blue){
        red = red/255;
        green = green/255;
        blue = blue/255;
        float min = min(red, min(green, blue));
        float max = max(red, max(green, blue));
        float l = (min + max) / 2;
        float h = 0, S;
        if(min == max){
            return new float[]{0,l,0};
        }
        S = l <= 0.5 ? (max-min)/(max+min) : (float) ((max - min) / (2.0 - max - min));
        if(red >= green && red >=blue){
            h=(green-blue)/(max-min);
        } else if (green >= red && green >= blue) {
            h= (float) (2.0 + (blue-red)/(max-min));
        } else if (blue >= red && blue>= green){
            h= (float) (4.0 + (red-green)/(max-min));
        }
        return new float[]{h, l, S};
    }

    public static float[] RGB_to_CMYK(float R, float G, float B){
        float tmp = max(R, max(G,B));
        float K = ((1 - tmp / 255));
        float C = ((1 - R/255 - K)/(1-K));
        float M = ((1-  G / 255 - K)/(1 - K));
        float Y = ((1-  B / 255 - K)/(1 - K));
        return new float[]{C, M, Y, K};
    }
    public static float[] CMYK_to_RGB(float C, float M, float Y, float K){

        float R = (255*(1-C/100)*(1-K/100));
        float G = (255*(1-M/100)*(1-K/100));
        float B = (255*(1-Y/100)*(1-K/100));
        return  new float[] {R, G, B};
    }

    private void updateRGB(Color color) {
        rField.setText(String.valueOf(color.getRed()));
        gField.setText(String.valueOf(color.getGreen()));
        bField.setText(String.valueOf(color.getBlue()));
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(ConverterColor::new);
    }
}