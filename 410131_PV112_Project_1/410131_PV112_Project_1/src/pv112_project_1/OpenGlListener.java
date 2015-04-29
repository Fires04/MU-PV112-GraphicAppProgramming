/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package pv112_project_1;

import com.jogamp.opengl.util.gl2.GLUT;
import java.awt.Cursor;
import java.awt.Point;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import java.util.List;
import static javax.media.opengl.GL.GL_FRONT_AND_BACK;
import javax.media.opengl.GL2;
import static javax.media.opengl.GL2.*;
import static javax.media.opengl.GL2GL3.GL_LINE;
import javax.media.opengl.GLAutoDrawable;
import javax.media.opengl.GLEventListener;
import static javax.media.opengl.fixedfunc.GLLightingFunc.GL_AMBIENT;
import static javax.media.opengl.fixedfunc.GLLightingFunc.GL_AMBIENT_AND_DIFFUSE;
import static javax.media.opengl.fixedfunc.GLLightingFunc.GL_COLOR_MATERIAL;
import static javax.media.opengl.fixedfunc.GLLightingFunc.GL_DIFFUSE;
import static javax.media.opengl.fixedfunc.GLLightingFunc.GL_LIGHT0;
import static javax.media.opengl.fixedfunc.GLLightingFunc.GL_LIGHTING;
import static javax.media.opengl.fixedfunc.GLLightingFunc.GL_POSITION;
import static javax.media.opengl.fixedfunc.GLLightingFunc.GL_SHININESS;
import static javax.media.opengl.fixedfunc.GLLightingFunc.GL_SPECULAR;
import static javax.media.opengl.fixedfunc.GLLightingFunc.GL_SPOT_CUTOFF;
import static javax.media.opengl.fixedfunc.GLLightingFunc.GL_SPOT_DIRECTION;
import static javax.media.opengl.fixedfunc.GLLightingFunc.GL_SPOT_EXPONENT;
import static javax.media.opengl.fixedfunc.GLMatrixFunc.GL_MODELVIEW;
import static javax.media.opengl.fixedfunc.GLMatrixFunc.GL_MODELVIEW_MATRIX;
import static javax.media.opengl.fixedfunc.GLMatrixFunc.GL_PROJECTION;
import javax.media.opengl.glu.GLU;


/**
 *
 * @author Jakub Kremláček
 * 
 * @sources 
 *      PV112 excercises - inicializace a zakladni metody panelu
 *      coobird - zneviditelneni kurzoru - link: http://stackoverflow.com/questions/1984071/how-to-hide-cursor-in-a-swing-application
 */
public class OpenGlListener implements GLEventListener, KeyListener, MouseMotionListener{
    
    //jPanel se ziskava z main metody, zasah do nej muze zpusobit neocekavane chovani programu
    private javax.swing.JPanel jPanel2;

    private GLU glu;
    private GLUT glut;
    
    private float angle = 0;
    private float speed = 0.5f;
    
    private float inclination = 0;
    private float rotation = 0;
    
    private float sensitivity = 0.03f;
    private float movementSpeed = 0.3f;
    
    private float xCamera = 0.5f;
    private float yCamera = 0.5f;
    private float zCamera = 0.5f;
    
    private float xCameraAngle = 0;
    private float yCameraAngle = 0;
    private float zCameraAngle = 0;
    
    private float vertexDenom = 0;
    
    private int xMouse = 100;
    private int yMouse = 100;
    private Robot robot;
    
    private boolean mouseMovement = false;
 
    private ObjectModel clock;
    private ObjectModel clockHourHand;
    private ObjectModel clockMinuteHand;
    private ObjectModel library;
    private ObjectModel table;
    private ObjectModel bin;
    private ObjectModel cabinet;
    private ObjectModel cabinetLDr;
    private ObjectModel cabinetRDr;
    
    private static final float black[] = {0.0f, 0.0f, 0.0f, 0.0f};
    private static final float red[] = {1.0f, 0.0f, 0.0f, 0.0f};
    private static final float green[] = {0.0f, 1.0f, 0.0f, 0.0f};
    private static final float blue[] = {0.0f, 0.0f, 1.0f, 0.0f};
    private static final float cyan[] = {0.0f, 1.0f, 1.0f, 0.0f};
    private static final float magenta[] = {1.0f, 0.0f, 1.0f, 0.0f};
    private static final float yellow[] = {1.0f, 1.0f, 0.0f, 0.0f};
    private static final float white[] = {1.0f, 1.0f, 1.0f, 0.0f};
    
    float[] viewMatrix;
    float[] clockViewMatrix;
    float[] cabinetViewMatrix;
    
    OpenGlListener(Robot robot, javax.swing.JPanel jPanel2) {   
        this.robot = robot;
        this.jPanel2 = jPanel2;
        
        clock = new ObjectModel("/resources/clock.obj");
        clockHourHand = new ObjectModel("/resources/clock-hourhand.obj");
        clockMinuteHand = new ObjectModel("/resources/clock-minutehand.obj");
        library = new ObjectModel("/resources/library.obj");
        table = new ObjectModel("/resources/table.obj");
        bin = new ObjectModel("/resources/bin.obj");
        cabinet = new ObjectModel("/resources/cabinet.obj");
        cabinetLDr = new ObjectModel("/resources/cabinet-leftdoor.obj");
        cabinetRDr = new ObjectModel("/resources/cabinet-rightdoor.obj");
        
    }
    
    @Override
    public void init(GLAutoDrawable glad) {
        GL2 gl = glad.getGL().getGL2();          
       
        glu = new GLU();
        glut = new GLUT();
        
        gl.glClearColor(0.3f, 0.3f, 0.3f, 0.0f); 
        gl.glClearDepthf(1.0f);
       
        gl.glEnable(GL_DEPTH_TEST);
        
        gl.glEnable(GL_LIGHTING);
        gl.glEnable(GL_COLOR_MATERIAL);
        
        gl.glEnable(GL_NORMALIZE);
        
        gl.glCullFace(GL_BACK);
        gl.glEnable(GL_CULL_FACE);
        
        viewMatrix = new float[16];
        clockViewMatrix = new float[16];
        cabinetViewMatrix = new float[16];
        
        
    }
    
    @Override
    public void dispose(GLAutoDrawable glad) {
    }
    
    @Override
    public void display(GLAutoDrawable glad) {
        GL2 gl = glad.getGL().getGL2(); 
        gl.glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT );
        
        gl.glLoadIdentity();
        
        if (inclination >= 1) {
            inclination = 1-0.00001f;
        } else if (inclination <= -1) {
            inclination = -1+0.00001f;
        }
        //animator
        angle+=speed;
        
        //normovani vektoru
        xCameraAngle = (float) Math.cos(rotation);
        yCameraAngle = (float) -Math.tan((inclination*Math.PI)/2);
        zCameraAngle = (float) Math.sin(rotation);
        
        vertexDenom = (float) Math.pow(xCameraAngle, 2) + (float) Math.pow(yCameraAngle, 2) + (float) Math.pow(zCameraAngle, 2);
        
        xCameraAngle /= vertexDenom;
        yCameraAngle /= vertexDenom;
        zCameraAngle /= vertexDenom;
        
        //souradnice kamery, upravene pomoci hodnot z jednotkove kruznice, urcuji vektor pohledu kamery
        glu.gluLookAt(  xCamera*10, yCamera*10, zCamera*10,
                        xCamera*10 + xCameraAngle, yCamera*10 + yCameraAngle, zCamera*10 + zCameraAngle,
                        0, 1, 0);
        
        gl.glScalef(0.05f, 0.05f, 0.05f);
        gl.glGetFloatv(GL_MODELVIEW_MATRIX, viewMatrix, 0);
        
        gl.glTranslatef(100, 150, -10);
        gl.glGetFloatv(GL_MODELVIEW_MATRIX, clockViewMatrix, 0);
        
        gl.glLoadMatrixf(viewMatrix, 0);
        gl.glTranslatef(320, 0, 200);
        gl.glRotatef(-90, 0, 1, 0);
        gl.glGetFloatv(GL_MODELVIEW_MATRIX, cabinetViewMatrix, 0);

        //odlesk
//        gl.glMaterialfv(GL_FRONT_AND_BACK, GL_AMBIENT_AND_DIFFUSE, white, 0);
//        gl.glMaterialfv(GL_FRONT_AND_BACK, GL_SPECULAR, white, 0);
//        gl.glMaterialf(GL_FRONT_AND_BACK, GL_SHININESS, 20.0f);
        
        //modely        
        gl.glColor3f(1, 1, 0);       
        
        gl.glLoadMatrixf(viewMatrix, 0);
        gl.glScalef(20f, 20f, 20f);
        gl.glTranslatef(10,0,10);
        gl.glRotatef(180, 1, 0, 0);
        drawFloor(gl, -15, 15, -15, 15, 0.5f, 0.5f);
        
        gl.glLoadMatrixf(viewMatrix, 0);
        gl.glTranslatef(70,81,20);
        gl.glRotatef(40, 0, 1, 0);
        gl.glScalef(7f, 7f, 7f);
        glut.glutSolidTeapot(2);
        
        
        gl.glLoadMatrixf(clockViewMatrix, 0);
        gl.glColor3f(0, 0, 0.5f);
        clock.draw(gl);
        
        gl.glLoadMatrixf(clockViewMatrix, 0);
        gl.glColor3f(1, 1, 0);
        //uprava pivotu
        gl.glTranslatef(0, -4.5f, 0);
        gl.glRotatef(-angle, 0, 0, 1);
        gl.glTranslatef(0, 4.5f, 0);
        clockMinuteHand.draw(gl);
        
        gl.glLoadMatrixf(clockViewMatrix, 0);
        // uprava pivotu
        gl.glTranslatef(0, -4.5f, 0);
        gl.glRotatef(-angle/12, 0, 0, 1);
        gl.glTranslatef(0, 4.5f, 0);
        clockHourHand.draw(gl);
     
        gl.glColor3f((float)139/256, (float)84/256, 0);
        
        gl.glLoadMatrixf(viewMatrix, 0);
        gl.glTranslatef(200, 0, 0);      
        library.draw(gl);
        
        gl.glLoadMatrixf(viewMatrix, 0);
        gl.glTranslatef(280, 0, 0);  
        library.draw(gl);
        
        gl.glLoadMatrixf(viewMatrix, 0);
        gl.glTranslatef(340, 0, 80);
        gl.glRotatef(-90, 0, 1, 0);
        library.draw(gl);
        
        gl.glColor3f((float)196/256, (float)119/256, 0);
        
        gl.glLoadMatrixf(viewMatrix, 0);
        gl.glTranslatef(100, 0, 30);
        table.draw(gl);
        
        gl.glLoadMatrixf(cabinetViewMatrix, 0);
        cabinet.draw(gl);
        cabinetLDr.draw(gl);
        cabinetRDr.draw(gl);
        
        gl.glColor3f((float)84/256, (float)83/256, (float)78/256);
        
        gl.glLoadMatrixf(viewMatrix, 0);
        gl.glTranslatef(120, 0, 20);
        bin.draw(gl);
        
        //svetlo
        gl.glLoadMatrixf(viewMatrix, 0);      
        gl.glTranslatef(200, 200, 50);
        gl.glEnable(GL_LIGHT0);
        gl.glColor3f(1, 1, 0);
        glut.glutSolidSphere(3, 10, 10);
        
        float light_0_direction[] = {0,-10,0,1};
        gl.glLightfv(GL_LIGHT0, GL_SPOT_DIRECTION, light_0_direction, 0); 
        gl.glLightf(GL_LIGHT0, GL_SPOT_CUTOFF, 60); 
        gl.glLightf(GL_LIGHT0, GL_SPOT_EXPONENT, 4); 
        
        float light_0_position[] = {-10,10,10,1};
        gl.glLightfv(GL_LIGHT0, GL_POSITION, light_0_position, 0); 
        gl.glLightfv(GL_LIGHT0, GL_AMBIENT, black, 0);  
        gl.glLightfv(GL_LIGHT0, GL_DIFFUSE, white, 0);  
        gl.glLightfv(GL_LIGHT0, GL_SPECULAR, white, 0);
        
//        gl.glLoadMatrixf(viewMatrix, 0);      
//        gl.glTranslatef(100, 10, 0);
//        gl.glEnable(GL_LIGHT1);
//        gl.glColor3f(1, 1, 0);
//        glut.glutSolidSphere(3, 10, 10);
//        
//        float light_1_direction[] = {0,10,0,1};
//        gl.glLightfv(GL_LIGHT1, GL_SPOT_DIRECTION, light_1_direction, 0); 
//        gl.glLightf(GL_LIGHT1, GL_SPOT_CUTOFF, 60); 
//        gl.glLightf(GL_LIGHT1, GL_SPOT_EXPONENT, 4); 
//        
//        float light_1_position[] = {-10,10,10,1};
//        gl.glLightfv(GL_LIGHT1, GL_POSITION, light_1_position, 0); 
//        gl.glLightfv(GL_LIGHT1, GL_AMBIENT, black, 0);  
//        gl.glLightfv(GL_LIGHT1, GL_DIFFUSE, yellow, 0);  
//        gl.glLightfv(GL_LIGHT1, GL_SPECULAR, yellow, 0);
        
        if (mouseMovement){
            robot.mouseMove(xMouse, yMouse);
        }
    }
    
    private void drawFloor(GL2 gl, float x_from, float x_to, float z_from, float z_to, float x_step, float z_step) {
        gl.glNormal3f(0.0f, -1.0f, 0.0f);
        
        for (float z = z_from; z <= z_to; z += z_step) {
            gl.glBegin(GL_QUAD_STRIP);
            for (float x = x_from; x <= x_to; x += x_step) {
                gl.glVertex3f(x, 0, z + z_step);
                gl.glVertex3f(x, 0, z);
            }
            gl.glEnd();
        } 
    }

    public void setSpeed(float speed) {
        this.speed = speed;
    }

    public void setSensitivity(float sensitivity) {
        this.sensitivity = sensitivity;
    }

    public void setMovementSpeed(float movementSpeed) {
        this.movementSpeed = movementSpeed;
    }
    
    @Override
    public void reshape(GLAutoDrawable glad, int x, int y, int width, int height) {
        GL2 gl = glad.getGL().getGL2();
        gl.glViewport(x, y, width, height);
        
        gl.glMatrixMode(GL_PROJECTION);
        gl.glLoadIdentity();
        
        //gl.glOrthof(-1, 1, -1, 1, -1, 50);
        //gl.glFrustumf(-1, 1, -1, 1, 5, 50);
        
        glu.gluPerspective(45, width/(float)height, 1, 50);
        
        gl.glMatrixMode(GL_MODELVIEW);

    }

    @Override
    public void keyTyped(KeyEvent evt) {
    }

    @Override
    public void keyPressed(KeyEvent evt) {
        switch (evt.getKeyCode()) {
            case KeyEvent.VK_ESCAPE:
                System.exit(0);
                break;
            case KeyEvent.VK_UP:
                this.inclination -= sensitivity;
                break;
            case KeyEvent.VK_DOWN:
                this.inclination += sensitivity;
                break;    
            case KeyEvent.VK_LEFT:
                this.rotation -= sensitivity;
                break;
            case KeyEvent.VK_RIGHT:
                this.rotation += sensitivity;
                break;
            case KeyEvent.VK_R:
                rotation = 0;
                inclination = 0;
                xCamera = 0.5f;
                yCamera = 0.5f;
                zCamera = 0.5f;
                break;
            case KeyEvent.VK_W:
                this.xCamera += xCameraAngle * movementSpeed;
                this.yCamera += yCameraAngle * movementSpeed;
                this.zCamera += zCameraAngle * movementSpeed;
                break;
            case KeyEvent.VK_S:
                this.xCamera -= xCameraAngle * movementSpeed;
                this.yCamera -= yCameraAngle * movementSpeed;
                this.zCamera -= zCameraAngle * movementSpeed;
                break;
            case KeyEvent.VK_A:
                this.xCamera += (float) Math.sin(rotation) * movementSpeed/1.5f;
                this.zCamera -= (float) Math.cos(rotation) * movementSpeed/1.5f;
                break;
            case KeyEvent.VK_D:
                this.xCamera -= (float) Math.sin(rotation) * movementSpeed/1.5f;
                this.zCamera += (float) Math.cos(rotation) * movementSpeed/1.5f;
                break;
            case KeyEvent.VK_Q:
                this.yCamera -= movementSpeed/1.5f;
                break;
            case KeyEvent.VK_E:
                this.yCamera += movementSpeed/1.5f;
                break;
            case KeyEvent.VK_X:
                toggleMouseControl();
                break;
            default:
        }
    }
    
    public void toggleMouseControl() {
        if(mouseMovement){
            mouseMovement = false;
            jPanel2.setCursor(Cursor.getDefaultCursor());
        } else {
            mouseMovement = true;
            BufferedImage cursorImg = new BufferedImage(16, 16, BufferedImage.TYPE_INT_ARGB);

            Cursor blankCursor = Toolkit.getDefaultToolkit().createCustomCursor(
                cursorImg, new Point(0, 0), "blank cursor");

            jPanel2.setCursor(blankCursor);

            xMouse = (int) jPanel2.getLocationOnScreen().getX() + jPanel2.getWidth()/2;
            yMouse = (int) jPanel2.getLocationOnScreen().getY() + jPanel2.getHeight()/2;
        }
    }

    @Override
    public void keyReleased(KeyEvent evt) {
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        if(mouseMovement) {
            float dX = ((jPanel2.getWidth()/2)-e.getX())*sensitivity;
            float dY = ((jPanel2.getHeight()/2)-e.getY())*sensitivity;

            if(dY != 0 || dX != 0){
                rotation -= dX;
                inclination -= dY;
            }
        }
        
    }
}
