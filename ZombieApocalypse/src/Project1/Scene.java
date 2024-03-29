/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Project1;

import static javax.media.opengl.GL2.*;
import javax.media.opengl.GL2;
import javax.media.opengl.GLAutoDrawable;
import javax.media.opengl.GLEventListener;
import com.jogamp.opengl.util.gl2.GLUT;
import static javax.media.opengl.GL.GL_BACK;
import static javax.media.opengl.GL.GL_COLOR_BUFFER_BIT;
import static javax.media.opengl.GL.GL_CULL_FACE;
import static javax.media.opengl.GL.GL_DEPTH_BUFFER_BIT;
import static javax.media.opengl.GL.GL_DEPTH_TEST;
import static javax.media.opengl.GL.GL_FLOAT;
import static javax.media.opengl.GL.GL_FRONT_AND_BACK;
import static javax.media.opengl.GL.GL_TRIANGLES;
import static javax.media.opengl.GL.GL_UNSIGNED_INT;
import static javax.media.opengl.fixedfunc.GLLightingFunc.GL_AMBIENT;
import static javax.media.opengl.fixedfunc.GLLightingFunc.GL_COLOR_MATERIAL;
import static javax.media.opengl.fixedfunc.GLLightingFunc.GL_DIFFUSE;
import static javax.media.opengl.fixedfunc.GLLightingFunc.GL_LIGHT0;
import static javax.media.opengl.fixedfunc.GLLightingFunc.GL_LIGHT1;
import static javax.media.opengl.fixedfunc.GLLightingFunc.GL_LIGHTING;
import static javax.media.opengl.fixedfunc.GLLightingFunc.GL_NORMALIZE;
import static javax.media.opengl.fixedfunc.GLLightingFunc.GL_POSITION;
import static javax.media.opengl.fixedfunc.GLLightingFunc.GL_SHININESS;
import static javax.media.opengl.fixedfunc.GLLightingFunc.GL_SMOOTH;
import static javax.media.opengl.fixedfunc.GLLightingFunc.GL_SPECULAR;
import static javax.media.opengl.fixedfunc.GLLightingFunc.GL_SPOT_CUTOFF;
import static javax.media.opengl.fixedfunc.GLLightingFunc.GL_SPOT_DIRECTION;
import static javax.media.opengl.fixedfunc.GLMatrixFunc.GL_MODELVIEW;
import static javax.media.opengl.fixedfunc.GLMatrixFunc.GL_MODELVIEW_MATRIX;
import static javax.media.opengl.fixedfunc.GLMatrixFunc.GL_PROJECTION;
import static javax.media.opengl.fixedfunc.GLPointerFunc.GL_NORMAL_ARRAY;
import static javax.media.opengl.fixedfunc.GLPointerFunc.GL_VERTEX_ARRAY;
import javax.media.opengl.glu.GLU;

/**
 *
 * @author milos_000
 */
public class Scene implements GLEventListener
{
    private GLU glu;
    private GLUT glut;
    private ObjLoader modelFloor;
    private ObjLoader modelCabinet;
    private ObjLoader model3;
    
    private float time;
    private float amplitude;
    private float radius;
    private float azimuth;
    private float altitude;
    private float[] cartesian;
    private float scale;
    private float[] lightPos;
    private float[] lightPos2;
    private float[] diffuseMat;
    private float XPos;
    private float YPos;
    private float ZPos;
    
    float[] viewMatrix;
        float[] clockViewMatrix;
    float[] cabinetViewMatrix;
    private final ObjLoader clock;
    
    
    public Scene()
    {
        //nacteni modelu
        modelFloor = new ObjLoader("\\resources\\Floor.obj");
        modelCabinet = new ObjLoader("\\resources\\cabinet.obj");
        clock = new ObjLoader("\\resources\\clock.obj");
        
        
        
        cartesian = new float[3];
        lightPos = new float[]{ 6, 4, 6, 1};
        lightPos2 = new float[]{ 4, 4, -4, 1};
        diffuseMat = new float[]{.50f, .50f, .50f}; 
        
        time = 0;
        amplitude = 2;
        radius = 4;
        azimuth = 0;
        altitude = 0;
    }

    @Override
    public void init(GLAutoDrawable drawable)
    {
        //ziskame si objekt openGl2
        GL2 gl = drawable.getGL().getGL2();
        
        glu = new GLU();
        glut = new GLUT();
                
       //  Povolit svetla
       gl.glEnable(GL_LIGHTING);
       gl.glEnable(GL_NORMALIZE);
       
       //nastaveni vychozich hodnot pozadi
       gl.glClearDepth(1.0f);
       gl.glClearColor(.3f,.3f,.3f,0);
        
       //Povolit Depth test - kontrola toho co je vskutku nutne vykreslit
       gl.glEnable(GL_DEPTH_TEST);
       //zapnuti barev materialu
       gl.glEnable(GL_COLOR_MATERIAL);
       //osekame zadni strany
       gl.glCullFace(GL_BACK);
       gl.glEnable(GL_CULL_FACE);
       

       //   Light 0
       float[] diffuseLight0 = {1.0f, 1.0f, 1.0f}; 
       float[] specularLight0 = {1.0f, 1.0f, 1.0f}; 
       gl.glLightfv(GL_LIGHT0, GL_DIFFUSE, diffuseLight0, 0);
       //gl.glLightf(GL_LIGHT0, GL_SPOT_CUTOFF, 20.0f);
       gl.glLightfv(GL_LIGHT0, GL_SPECULAR, specularLight0, 0);
       gl.glEnable(GL_LIGHT0);
//       
       //   Light 2
//       float[] diffuseLight2 = {0.88f, 0.88f, 0.0f}; 
//       float[] specularLight2 = {0.78f, 0.78f, 0.0f};
//
//       gl.glLightfv(GL_LIGHT1, GL_DIFFUSE, diffuseLight2, 0);
//       gl.glLightfv(GL_LIGHT1, GL_SPECULAR, specularLight2, 0);       

         // Material
//       float[] ambientMat = {0.1f, 0.1f, 0.1f}; 
//       float[] specularMat = {1.0f, 1.0f, 1.0f};
       
//       gl.glMaterialfv(GL_FRONT_AND_BACK, GL_DIFFUSE, diffuseMat, 0);
//       gl.glMaterialfv(GL_FRONT_AND_BACK, GL_AMBIENT, ambientMat, 0);
//       gl.glMaterialfv(GL_FRONT_AND_BACK, GL_SPECULAR, specularMat, 0);
//       gl.glMaterialf(GL_FRONT_AND_BACK, GL_SHININESS, 100);
    }

    @Override
    public void dispose(GLAutoDrawable drawable) 
    {
    }

    @Override
    public void display(GLAutoDrawable drawable) 
    {
        GL2 gl = drawable.getGL().getGL2();
        time += 0.01;
        
        gl.glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);  
                
        
       
        //  Camera
        polarCoordinates(azimuth, altitude, cartesian);
        glu.gluLookAt(cartesian[0] + XPos, cartesian[1] + YPos, cartesian[2] + ZPos, XPos, YPos, ZPos, 0, 1, 0);
        
//        // Light 1 position
//        gl.glPushMatrix();
//            gl.glRotated(time * 20, 0, 1, 0);
//            gl.glTranslatef(lightPos[0], lightPos[1], lightPos[2]);    
//            gl.glLightfv(GL_LIGHT0, GL_POSITION, new float[]{0,0,0,1}, 0);
//            gl.glLightfv(GL_LIGHT0, GL_SPOT_DIRECTION, new float[]{-1,-0.5f,-1},0);
//            glut.glutSolidSphere(0.2, 10, 10);
//        gl.glPopMatrix();
        
//        // Light 2 position
//        gl.glPushMatrix();
//            gl.glTranslatef(lightPos2[0], lightPos2[1], lightPos2[2]);    
//            gl.glLightfv(GL_LIGHT1, GL_POSITION, lightPos2, 0);
//            glut.glutSolidSphere(0.2, 10, 10);
//        gl.glPopMatrix();
        
        // Draw Floor
        // zmena materialu floor
        //gl.glMaterialfv(GL_FRONT_AND_BACK, GL_DIFFUSE, new float[]{ 1.0f, 0.0f,0 }, 0);
        //drawFloor(gl, -10, 10, -10, 10, .1f, .1f);
        
        
        
        //float move = amplitude * (float)Math.cos(2 * Math.PI * time);
        
        // zmena materialu pre konvicky
//        gl.glMaterialfv(GL_FRONT_AND_BACK, GL_DIFFUSE, diffuseMat, 0);
//        
//        //  Model 1
//        gl.glPushMatrix();
//            gl.glRotatef(time * 45, 0, 1, 0);
//            gl.glTranslatef(radius, amplitude + 1 + move, 0);
//            glut.glutSolidTeapot(1f);
//        gl.glPopMatrix();
//        
//        //  Model 2
//        gl.glPushMatrix();
//            gl.glRotatef(time * 45 + 90, 0, 1, 0);
//            move = amplitude * (float)Math.cos(2 * Math.PI * time + 1);
//            gl.glTranslatef(radius, amplitude + 1 + move , 0);
//            glut.glutSolidTeapot(1f);
//        gl.glPopMatrix();
//        
//        //  Model 3
//        gl.glPushMatrix();
//            gl.glRotatef(time * 45 + 180, 0, 1, 0);
//            move = amplitude * (float)Math.cos(2 * Math.PI * time + 2);
//            gl.glTranslatef(radius, amplitude + 1 + move, 0);
//            glut.glutSolidTeapot(1f);
//        gl.glPopMatrix();
//        
//        //  Model 4
//        gl.glPushMatrix();
//            gl.glRotatef(time * 45 + 270, 0, 1, 0);
//            move = amplitude * (float)Math.cos(2 * Math.PI * time + 3);
//            gl.glTranslatef(radius, amplitude + 1 + move, 0);
//            glut.glutSolidTeapot(1f);
//        gl.glPopMatrix();
        
        
        
//        //Vykreslime si system souradnic
//        gl.glPushMatrix();
//        drawCoordinateSystem(gl);
//        gl.glPopMatrix();
//        
//        //podlaha
//        gl.glPushMatrix();
//         // Material
//        float[] ambientMat = {0.5f, 0.5f, 0.5f}; 
//        float[] specularMat = {1.0f, 1.0f, 1.0f};  
//        gl.glMaterialfv(GL_FRONT_AND_BACK, GL_DIFFUSE, diffuseMat, 0);
//        gl.glMaterialfv(GL_FRONT_AND_BACK, GL_AMBIENT, ambientMat, 0);
//        gl.glMaterialfv(GL_FRONT_AND_BACK, GL_SPECULAR, specularMat, 0);
//        drawObj(gl, modelFloor);
//        gl.glScaled(0.5, 0.5, 0.5);
//        gl.glPopMatrix();
//        
//        
//        gl.glPushMatrix();
//        gl.glScaled(0.1, 0.1, 0.1);
//        gl.glTranslated(0, 0, 0);
//        drawObj(gl, modelCabinet);
//        gl.glPopMatrix();
        
        viewMatrix = new float[16];
        clockViewMatrix = new float[16];
        cabinetViewMatrix = new float[16];
        
        
        
        gl.glScalef(0.05f, 0.05f, 0.05f);
        gl.glGetFloatv(GL_MODELVIEW_MATRIX, viewMatrix, 0);
        
        gl.glTranslatef(100, 150, -10);
        gl.glGetFloatv(GL_MODELVIEW_MATRIX, clockViewMatrix, 0);
        
        gl.glLoadMatrixf(viewMatrix, 0);
        gl.glTranslatef(320, 0, 200);
        gl.glRotatef(-90, 0, 1, 0);
        gl.glGetFloatv(GL_MODELVIEW_MATRIX, cabinetViewMatrix, 0);
        
        
        gl.glColor3f(1, 1, 0);   
        
        gl.glLoadMatrixf(viewMatrix, 0);
        gl.glTranslatef(70,81,20);
        gl.glRotatef(40, 0, 1, 0);
        gl.glScalef(7f, 7f, 7f);
        glut.glutSolidTeapot(2);
        
        gl.glLoadMatrixf(clockViewMatrix, 0);
        gl.glColor3f(0, 0, 0.5f);
        drawObj(gl, clock);
        

    }

    @Override
    public void reshape(GLAutoDrawable drawable, int x, int y, int width, int height)
    {
        GL2 gl = drawable.getGL().getGL2();
        gl.glShadeModel(GL_SMOOTH);
        gl.glMatrixMode(GL_PROJECTION);
        gl.glLoadIdentity();
        glu.gluPerspective(55, (float)width/height, 1, 1000);
        gl.glViewport(0, 0, width, height);
        gl.glMatrixMode(GL_MODELVIEW);
    }
    
    private void drawFloor(GL2 gl, float x_from, float x_to, float z_from, float z_to, float x_step, float z_step) {
        gl.glNormal3f(0.0f, 1.0f, 0.0f);
        for (float z = z_from; z < z_to; z += z_step) {
            gl.glBegin(GL_QUAD_STRIP);
            for (float x = x_from; x <=  x_to; x += x_step) {
                gl.glVertex3f(x, 0, z);
                gl.glVertex3f(x, 0, z + z_step);
            }
            gl.glEnd();
        }
    }
    
    private void drawCoordinateSystem(GL2 gl) {
        gl.glPushMatrix();
        //OSA X - zelena
        gl.glTranslatef(0, 0, 0);
        gl.glRotatef(90, 0, 1, 0);
        gl.glMaterialfv(GL_FRONT_AND_BACK, GL_AMBIENT, new float[]{0, 1, 0}, 0);
        gl.glMaterialfv(GL_FRONT_AND_BACK, GL_DIFFUSE, new float[]{0, 1, 0}, 0);
        gl.glMaterialfv(GL_FRONT_AND_BACK, GL_SPECULAR, new float[]{0, 1, 0}, 0);
        gl.glMaterialf(GL_FRONT_AND_BACK, GL_SHININESS, 100); // teoreticky rozptyl odrazu
        glut.glutSolidCylinder(0.2, 5, 100, 100);
        gl.glPopMatrix();
        gl.glPushMatrix();
        //OSA Y - modra
        gl.glTranslatef(0, 5, 0);
        gl.glRotatef(90, 1, 0, 0);
        gl.glMaterialfv(GL_FRONT_AND_BACK, GL_AMBIENT, new float[]{0, 0, 1}, 0);
        gl.glMaterialfv(GL_FRONT_AND_BACK, GL_DIFFUSE, new float[]{0, 0, 1}, 0);
        gl.glMaterialfv(GL_FRONT_AND_BACK, GL_SPECULAR, new float[]{0, 0, 1}, 0);
        gl.glMaterialf(GL_FRONT_AND_BACK, GL_SHININESS, 100); // teoreticky rozptyl odrazu
        glut.glutSolidCylinder(0.2, 5, 100, 100);
        gl.glPopMatrix();
        gl.glPushMatrix();
        //OSA Z - cervena
        gl.glTranslatef(0, 0, 0);
        gl.glRotatef(180, 0, 1, 0);
        gl.glMaterialfv(GL_FRONT_AND_BACK, GL_AMBIENT, new float[]{1, 0, 0}, 0);
        gl.glMaterialfv(GL_FRONT_AND_BACK, GL_DIFFUSE, new float[]{1, 0, 0}, 0);
        gl.glMaterialfv(GL_FRONT_AND_BACK, GL_SPECULAR, new float[]{1, 0, 0}, 0);
        gl.glMaterialf(GL_FRONT_AND_BACK, GL_SHININESS, 100); // teoreticky rozptyl odrazu
        glut.glutSolidCylinder(0.2, 5, 100, 100);
        gl.glPopMatrix();
    }
    
    
    private void polarCoordinates(float azimuth, float altitude, float[] cartesian)
    {
        float a = (float) (20 * Math.cos(Math.toRadians(altitude)));
        cartesian[0] = (float) (a * Math.cos(Math.toRadians(azimuth)));
        cartesian[1] = (float) (20 * Math.sin(Math.toRadians(altitude)));
        cartesian[2] = (float) (a * Math.sin(Math.toRadians(azimuth)));
    }
    
        
    
    private void drawObj(GL2 gl, ObjLoader model)
    {            
        gl.glEnableClientState(GL_VERTEX_ARRAY);
        gl.glEnableClientState(GL_NORMAL_ARRAY);
            gl.glVertexPointer(3, GL_FLOAT, 0, model.getVerticesBuffer());
            gl.glNormalPointer(GL_FLOAT, 0, model.getNormalsBuffer());
            // Vykreslenie celeho modelu jednym prikazom
            gl.glDrawElements(GL_TRIANGLES, model.getVertexIndicesBuffer().capacity(), GL_UNSIGNED_INT, model.getVertexIndicesBuffer());
        gl.glDisableClientState(GL_VERTEX_ARRAY);
        gl.glDisableClientState(GL_NORMAL_ARRAY);
    }   
    
    public void setAzimuth(float val)
    {
        this.azimuth = val;
    }
    
    public void setAltitude(float val)
    {
        this.altitude = val;
    }

    public void setXPos(float XPos) {
        this.XPos = XPos;
    }

    public void setYPos(float YPos) {
        this.YPos = YPos;
    }

    public void setZPos(float ZPos) {
        this.ZPos = ZPos;
    }
    

}