import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;

import static java.lang.Math.max;
import static java.lang.Math.min;

public class ColorConverter extends JFrame {

    private static final long serialVersionUID = 1L;

    private JSlider redSlider, greenSlider, blueSlider;
    private JSlider cyanSlider, magentaSlider, yellowSlider, blackSlider;
    private JSlider hueSlider, saturationSlider, lightnessSlider;
    private JTextField redField, greenField, blueField;
    private JTextField cyanField, magentaField, yellowField, blackField;
    private JTextField hueField, saturationField, lightnessField;
    private JLabel colorLabel;
    private JPanel colorPanel;

    public ColorConverter() {
        super("Color Converter");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);
        setLayout(new BorderLayout());

        // Панель для отображения цвета
        colorPanel = new JPanel();
        colorPanel.setBackground(Color.BLACK);
        add(colorPanel, BorderLayout.NORTH);

        // Панель для RGB-компонентов
        JPanel rgbPanel = new JPanel(new GridLayout(4, 2, 10, 10));
        rgbPanel.setBorder(BorderFactory.createTitledBorder("RGB"));
        add(rgbPanel, BorderLayout.WEST);

        redSlider = new JSlider(JSlider.HORIZONTAL, 0, 255, 0);
        redSlider.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                updateColorFromRGB();
            }
        });
        rgbPanel.add(new JLabel("Red:"));
        rgbPanel.add(redSlider);

        greenSlider = new JSlider(JSlider.HORIZONTAL, 0, 255, 0);
        greenSlider.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                updateColorFromRGB();
            }
        });
        rgbPanel.add(new JLabel("Green:"));
        rgbPanel.add(greenSlider);

        blueSlider = new JSlider(JSlider.HORIZONTAL, 0, 255, 0);
        blueSlider.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                updateColorFromRGB();
            }
        });
        rgbPanel.add(new JLabel("Blue:"));
        rgbPanel.add(blueSlider);

        redField = new JTextField("0");
        redField.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateColorFromRGB();
            }
        });
        rgbPanel.add(redField);

        greenField = new JTextField("0");
        greenField.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateColorFromRGB();
            }
        });
        rgbPanel.add(greenField);

        blueField = new JTextField("0");
        blueField.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateColorFromRGB();
            }
        });
        rgbPanel.add(blueField);

        // Панель для CMYK-компонентов
        JPanel cmykPanel = new JPanel(new GridLayout(5, 2, 10, 10));
        cmykPanel.setBorder(BorderFactory.createTitledBorder("CMYK"));
        add(cmykPanel, BorderLayout.CENTER);

        cyanSlider = new JSlider(JSlider.HORIZONTAL, 0, 255, 0);
        cyanSlider.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                updateColorFromCMYK();
            }
        });
        cmykPanel.add(new JLabel("Cyan:"));
        cmykPanel.add(cyanSlider);

        magentaSlider = new JSlider(JSlider.HORIZONTAL, 0, 255, 0);
        magentaSlider.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                updateColorFromCMYK();
            }
        });
        cmykPanel.add(new JLabel("Magenta:"));
        cmykPanel.add(magentaSlider);

        yellowSlider = new JSlider(JSlider.HORIZONTAL, 0, 255, 0);
        yellowSlider.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                updateColorFromCMYK();
            }
        });
        cmykPanel.add(new JLabel("Yellow:"));
        cmykPanel.add(yellowSlider);

        blackSlider = new JSlider(JSlider.HORIZONTAL, 0, 255, 0);
        blackSlider.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                updateColorFromCMYK();
            }
        });
        cmykPanel.add(new JLabel("Black:"));
        cmykPanel.add(blackSlider);

        cyanField = new JTextField("0");
        cyanField.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateColorFromCMYK();
            }
        });
        cmykPanel.add(cyanField);

        magentaField = new JTextField("0");
        magentaField.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateColorFromCMYK();
            }
        });
        cmykPanel.add(magentaField);

        yellowField = new JTextField("0");
        yellowField.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateColorFromCMYK();
            }
        });
        cmykPanel.add(yellowField);

        blackField = new JTextField("0");
        blackField.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateColorFromCMYK();
            }
        });
        cmykPanel.add(blackField);

        // Панель для HLS-компонентов
        JPanel hlsPanel = new JPanel(new GridLayout(4, 2, 10, 10));
        hlsPanel.setBorder(BorderFactory.createTitledBorder("HLS"));
        add(hlsPanel, BorderLayout.EAST);

        hueSlider = new JSlider(JSlider.HORIZONTAL, 0, 360, 0);
        hueSlider.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                updateColorFromHLS();
            }
        });
        hlsPanel.add(new JLabel("Hue:"));
        hlsPanel.add(hueSlider);

        saturationSlider = new JSlider(JSlider.HORIZONTAL, 0, 255, 0);
        saturationSlider.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                updateColorFromHLS();
            }
        });
        hlsPanel.add(new JLabel("Saturation:"));
        hlsPanel.add(saturationSlider);

        lightnessSlider = new JSlider(JSlider.HORIZONTAL, 0, 255, 0);
        lightnessSlider.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                updateColorFromHLS();
            }
        });
        hlsPanel.add(new JLabel("Lightness:"));
        hlsPanel.add(lightnessSlider);

        hueField = new JTextField("0");
        hueField.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateColorFromHLS();
            }
        });
        hlsPanel.add(hueField);

        saturationField = new JTextField("0");
        saturationField.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateColorFromHLS();
            }
        });
        hlsPanel.add(saturationField);

        lightnessField = new JTextField("0");
        lightnessField.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateColorFromHLS();
            }
        });
        hlsPanel.add(lightnessField);

        // Отображение выбранного цвета
        colorLabel = new JLabel();
        colorLabel.setPreferredSize(new Dimension(100, 100));
        colorLabel.setOpaque(true);
        colorLabel.setBackground(Color.BLACK);
        add(colorLabel, BorderLayout.SOUTH);

        setVisible(true);
    }

    public static float[] RGBtoCMYK(float red, float green, float blue){
        float tmp = max(red, max(green,blue));
        float black = ((1 - (float)tmp / 255));
        float cyan = ((1 - (float)red/255 - black)*(1-black));
        float magenta = ((1- (float) green / 255 - black)*(1 - black));
        float yellow = ((1- (float) blue / 255 - black)*(1 - black));
        return new float[]{cyan, magenta, yellow, black};
    }

    private static float[] CMYKtoRGB(float cyan, float magenta, float yellow, float black) {
        float r = (255*(1-(float)cyan)*(1-(float)black));
        float g = (255*(1-(float)magenta)*(1-(float)black));
        float b = (255*(1-(float)yellow)*(1-(float)black));
        return  new float[] {r,g,b};
    }

    public static float[] HLStoRGB(float H, float L, float S) {
        if (S == 0) {
            float color = (int) (L * 255);
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
    }

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


   // Методы обновления цвета
    private void updateColorFromRGB() {
        int red = Integer.parseInt(redField.getText());
        int green = Integer.parseInt(greenField.getText());
        int blue = Integer.parseInt(blueField.getText());
        Color color = new Color(red, green, blue);

        // Обновление слайдеров
        redSlider.setValue(red);
        greenSlider.setValue(green);
        blueSlider.setValue(blue);

        // Обновление полей ввода и цвета
        colorPanel.setBackground(color);
        colorLabel.setBackground(color);

        // Пересчет в CMYK и HLS

        float[] cmyk = RGBtoCMYK(red, green, blue);
        //float[] cmyk = Color.RGBtoCMYK(red, green, blue);
        float[] hls = RGBtoHLS(red, green, blue);

        cyanField.setText(String.format("%.2f", cmyk[0]));
        magentaField.setText(String.format("%.2f", cmyk[1]));
        yellowField.setText(String.format("%.2f", cmyk[2]));
        blackField.setText(String.format("%.2f", cmyk[3]));

        hueField.setText(String.format("%.2f", hls[0] * 360));
        saturationField.setText(String.format("%.2f", hls[1] * 255));
        lightnessField.setText(String.format("%.2f", hls[2] * 255));

        // Обновление слайдеров
        cyanSlider.setValue((int) (cmyk[0] * 255));
        magentaSlider.setValue((int) (cmyk[1] * 255));
        yellowSlider.setValue((int) (cmyk[2] * 255));
        blackSlider.setValue((int) (cmyk[3] * 255));

        hueSlider.setValue((int) (hls[0] * 360));
        saturationSlider.setValue((int) (hls[1] * 255));
        lightnessSlider.setValue((int) (hls[2] * 255));
    }

    private void updateColorFromCMYK() {
        float cyan = Float.parseFloat(cyanField.getText()) / 255;
        float magenta = Float.parseFloat(magentaField.getText()) / 255;
        float yellow = Float.parseFloat(yellowField.getText()) / 255;
        float black = Float.parseFloat(blackField.getText()) / 255;
        float[] rgb = CMYKtoRGB(cyan, magenta, yellow, black);
        Color color = new Color(rgb[0], rgb[1], rgb[2]);
        //Color color = new Color(CMYKtoRGB(cyan, magenta, yellow, black));

        // Обновление слайдеров
        cyanSlider.setValue((int) (cyan * 255));
        magentaSlider.setValue((int) (magenta * 255));
        yellowSlider.setValue((int) (yellow * 255));
        blackSlider.setValue((int) (black * 255));

        // Обновление полей ввода и цвета
        colorPanel.setBackground(color);
        colorLabel.setBackground(color);

        // Пересчет в RGB и HLS
        rgb = CMYKtoRGB(cyan, magenta, yellow, black);
        float[] hls = RGBtoHLS(rgb[0], rgb[1], rgb[2]);

        redField.setText(String.valueOf(rgb[0]));
        greenField.setText(String.valueOf(rgb[1]));
        blueField.setText(String.valueOf(rgb[2]));

        hueField.setText(String.format("%.2f", hls[0] * 360));
        saturationField.setText(String.format("%.2f", hls[1] * 255));
        lightnessField.setText(String.format("%.2f", hls[2] * 255));

        // Обновление слайдеров
        redSlider.setValue((int) rgb[0]);
        greenSlider.setValue((int) rgb[1]);
        blueSlider.setValue((int) rgb[2]);

        hueSlider.setValue((int) (hls[0] * 360));
        saturationSlider.setValue((int) (hls[1] * 255));
        lightnessSlider.setValue((int) (hls[2] * 255));
    }

    private void updateColorFromHLS() {
        float hue = Float.parseFloat(hueField.getText()) / 360;
        float saturation = Float.parseFloat(saturationField.getText()) / 255;
        float lightness = Float.parseFloat(lightnessField.getText()) / 255;
        float[] rgb = HLStoRGB(hue, saturation, lightness);

        Color color = new Color(rgb[0], rgb[1], rgb[2]);
        //Color color = new Color(HLStoRGB(hue, saturation, lightness));

        // Обновление слайдеров
        hueSlider.setValue((int) (hue * 360));
        saturationSlider.setValue((int) (saturation * 255));
        lightnessSlider.setValue((int) (lightness * 255));

        // Обновление полей ввода и цвета
        colorPanel.setBackground(color);
        colorLabel.setBackground(color);

        // Пересчет в RGB и CMYK
        rgb = HLStoRGB(hue, saturation, lightness);
        float[] cmyk = RGBtoCMYK(rgb[0], rgb[1], rgb[2]);

        redField.setText(String.valueOf(rgb[0]));
        greenField.setText(String.valueOf(rgb[1]));
        blueField.setText(String.valueOf(rgb[2]));

        cyanField.setText(String.format("%.2f", cmyk[0]));
        magentaField.setText(String.format("%.2f", cmyk[1]));
        yellowField.setText(String.format("%.2f", cmyk[2]));
        blackField.setText(String.format("%.2f", cmyk[3]));

        // Обновление слайдеров
        redSlider.setValue((int) rgb[0]);
        greenSlider.setValue((int) rgb[1]);
        blueSlider.setValue((int) rgb[2]);

        cyanSlider.setValue((int) (cmyk[0] * 255));
        magentaSlider.setValue((int) (cmyk[1] * 255));
        yellowSlider.setValue((int) (cmyk[2] * 255));
        blackSlider.setValue((int) (cmyk[3] * 255));
    }

    public static void main(String[] args) {
        new ColorConverter();
    }
}