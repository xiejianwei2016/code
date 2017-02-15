package code.verifycode;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Random;

import javax.imageio.ImageIO;

/**
 * 验证码生成器
 *
 * @author 谢建伟
 * @history
 * <TABLE id="HistoryTable" border="1">
 * 	<TR><TD>时间</TD><TD>描述</TD><TD>作者</TD></TR>
 *	<TR><TD>2016年3月23日</TD><TD>创建初始版本</TD><TD>谢建伟</TD></TR>
 * </TABLE>
 */
public class VerifyCode {

	// 图片的宽度。
    private int width = 160;
    // 图片的高度。
    private int height = 80;
    // 验证码字符个数
    private int codeCount = 5;
    // 验证码干扰线数 
    private int lineCount = 150;
    // 验证码  
    private String code = null;
    // 验证码图片Buffer
    private BufferedImage buffImg = null;
    //{ '0','1','2','3','4','5','6','7','8','9'}
    //{'\u96f6','\u4e00','\u4E8C','\u4E09','\u56DB','\u4E94','\u516D','\u4E03','\u516B','\u4E5D'}
    private char[] codeSequence = { '0','1','2','3','4','5','6','7','8','9'};
    //{'+','-','*'}
    //{'\u52a0','\u51cf','\u4e58'}
    private char[] op = {'+','-','*'};

    /**
     * 验证码构造器
     * @param width 图片宽
     * @param height 图片高
     * @param codeCount 字符个数
     * @param lineCount 干扰线条数
     */
    public VerifyCode(int width,int height,int codeCount,int lineCount) {
        this.width=width;
        this.height=height;
        this.codeCount=codeCount;
        this.lineCount=lineCount;
        this.createCode();
    }
    
    /**
     * 生成验证码
     */
    public void createCode() {
        int x = 0, codeY=0;
        int red = 0, green = 0, blue = 0;

        x = width / (codeCount +2);//每个字符的宽度 
        codeY = height - 10; //字体在图片上下的位置

        // 图像buffer
        buffImg = new BufferedImage(width, height,BufferedImage.TYPE_INT_RGB);
        Graphics2D g = buffImg.createGraphics();
        // 生成随机数 
        Random random = new Random();
        // 将图像填充为白色 
        g.setColor(Color.WHITE);
        g.fillRect(0, 0, width, height);
        
        //设置字体样式
        Font font = new Font("宋体", Font.BOLD, 18);
        g.setFont(font);

        for (int i = 0; i < lineCount; i++) {
            int xs = random.nextInt(width);
            int ys = random.nextInt(height);
            int xe = xs+random.nextInt(width/8);
            int ye = ys+random.nextInt(height/8);
            red = random.nextInt(255);
            green = random.nextInt(255);
            blue = random.nextInt(255);
            g.setColor(new Color(red, green, blue));
            g.drawLine(xs, ys, xe, ye);
        }

        // randomCode记录随机产生的验证码
        StringBuffer randomCode = new StringBuffer();
        // 随机产生codeCount个字符的验证码
        int suiji = (int)(Math.random()*3);
        int first = 0;
        int second = 0;
        for (int i = 0,j=0; i < 2; i++) {
        	int ss = random.nextInt(codeSequence.length);
            String strRand = String.valueOf(codeSequence[ss]);
            // 产生随机的颜色值，让输出的每个字符的颜色值都将不同
            red = random.nextInt(255);
            green = random.nextInt(255);
            blue = random.nextInt(255);
            g.setColor(new Color(red, green, blue));
            g.drawString(strRand, (j++ + 1) * x, codeY);
            // 将产生的四个随机数组合在一起。
            //randomCode.append(strRand);
            second=ss;
            //加载运算符
            if(i==0){
            	g.drawString(String.valueOf(op[suiji]), (j++ + 1) * x, codeY);
            	//randomCode.append(op[suiji]);
            	first=ss;
            }
        }
        //加载  “等于？”"\u7b49\u4e8e\uff1f"
        g.drawString("= ?", (3 + 1) * x, codeY);
        //randomCode.append("\u7b49\u4e8e\uff1f");
        
        //计算图片验证码值
        int result = 0;
        if(suiji==0){
        	result = first+second;
        }
        if(suiji==1){
        	result = first-second;
        }
        if(suiji==2){
        	result = first*second;
        }
        // 将四位数字的验证码保存到Session中。
        randomCode.append(result);
        code=randomCode.toString(); 
    }

    /**
     * 输出验证码图片
     * @param path "E:/verifies/1.png"
     * @throws IOException
     */
    public void write(String path) throws IOException {
        OutputStream sos = new FileOutputStream(path);
            this.write(sos);
    }
    
    /**
     * 输出验证码图片流
     * @param sos
     * @throws IOException
     */
    public void write(OutputStream sos) throws IOException {
            ImageIO.write(buffImg, "png", sos);
            sos.close();
    }
    
    /**
     * 验证码图片Buffer
     * @return
     */
    public BufferedImage getBuffImg() {
        return buffImg;
    }
    /**
     * 获取图片验证码计算后的值
     * @return
     */
    public String getCode() {
        return code;
    }

	public static void main(String[] args) throws IOException {
		//调用生成图片验证码类
		VerifyCode verifycode = new VerifyCode(95,36,5,30);
		verifycode.write("F:/verifies/"+verifycode.getCode()+".png");
	}
}

/**
 * Copyright © 2016,天安怡和 All rights reserved.
 */
