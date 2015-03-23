/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cv02_start;

import com.jogamp.opengl.util.gl2.GLUT;
import javax.media.opengl.GL;
import static javax.media.opengl.GL.GL_COLOR_BUFFER_BIT;
import static javax.media.opengl.GL.GL_FRONT;
import javax.media.opengl.GL2;
import static javax.media.opengl.GL2.*;
import static javax.media.opengl.GL2GL3.GL_LINE;
import javax.media.opengl.GLAutoDrawable;
import javax.media.opengl.GLEventListener;
import javax.media.opengl.glu.GLU;

/**
 *
 * @author dvdthepmkr
 */
public class Scene implements GLEventListener{

    private GLU glu = new GLU();
    private GLUT glut = new GLUT();
    
    @Override
    public void init(GLAutoDrawable glad) {
        // Ziskame si objekt GL2 ktory zpristupni funkcionalitu OpenGL
        GL2 gl = glad.getGL().getGL2();
        
        // Nastavime farbu pozadia naseho okna
        gl.glClearColor(0f, 0f, 0f, 1.0f);
       
    }

    @Override
    public void dispose(GLAutoDrawable glad) {
        
    }

    @Override
    public void display(GLAutoDrawable glad) {
        GL2 gl = glad.getGL().getGL2();
        
        gl.glClear(GL_COLOR_BUFFER_BIT);
        
        gl.glPolygonMode(GL_FRONT_AND_BACK, GL_LINE);
        
        gl.glBegin(GL_TRIANGLES);
            gl.glVertex3f(-0.2f, -0.2f, 0.0f);
            gl.glVertex3f(0.2f, -0.2f, 0.0f);
            gl.glVertex3f(0.0f, 0.2f, 0.0f);
        gl.glEnd();
        
        gl.glPolygonMode(GL_FRONT_AND_BACK, GL_FILL);
        
        glu.gluLookAt(0f, 0f, 0f, 0f, 0f, 0f, 0.5f, 0.5f, 0.5f);
        
        this.drawAxis(gl);
        this.drawFloor(gl);
    }

    @Override
    public void reshape(GLAutoDrawable glad, int i, int i1, int i2, int i3) {
        
    }

    private void drawAxis(GL2 gl) {
        // axis
        gl.glColor3f(0.5f, 0.5f, 0.5f);
        gl.glBegin(GL2.GL_LINES);
            // x axis
            gl.glVertex3f(0.0f, 0.0f, 0.0f);
            gl.glVertex3f(1.0f, 0.0f, 0.0f);
            
            //gl.glVertex3f(0.0f, 0.0f, 0.0f);
            //gl.glVertex3f(-1.0f, 0.0f, 0.0f);
            
            // y axis
            gl.glVertex3f(0.0f, 0.0f, 0.0f);
            gl.glVertex3f(0.0f, 1.0f, 0.0f);
            
            //gl.glVertex3f(0.0f, 0.0f, 0.0f);
            //gl.glVertex3f(0.0f, -1.0f, 0.0f);
            
            // z axis
            gl.glVertex3f(0.0f, 0.0f, 0.0f);
            gl.glVertex3f(0.0f, 0.0f, 1.0f);
            
            //gl.glVertex3f(0.0f, 0.0f, 0.0f);
            //gl.glVertex3f(0.0f, 0.0f, -1.0f);
        gl.glEnd();
    }
    
    private void drawFloor(GL2 gl) {
        float floorTileSize = 1.0f;
        gl.glBegin(GL2.GL_QUADS);
        
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                gl.glVertex3f(-2.5f + i*floorTileSize, 0, -1.5f + j*floorTileSize);
                gl.glVertex3f(-1.5f + i*floorTileSize, 0, -1.5f + j*floorTileSize);
                gl.glVertex3f(-1.5f + i*floorTileSize, 0, -2.5f + j*floorTileSize);
                gl.glVertex3f(-2.5f + i*floorTileSize, 0, -2.5f + j*floorTileSize);
            }           
        }
        gl.glEnd();
    }
 
    
}
