/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pkg20150223_pv112;

import javax.media.opengl.GL;
import static javax.media.opengl.GL.GL_FRONT;
import javax.media.opengl.GL2;
import static javax.media.opengl.GL2GL3.GL_FILL;
import static javax.media.opengl.GL2GL3.GL_LINE;
import javax.media.opengl.GLAutoDrawable;
import javax.media.opengl.GLEventListener;

/**
 *
 * @author xstein
 */
public class Scene implements GLEventListener{

    @Override
    public void init(GLAutoDrawable glad) {
        GL2 gl= glad.getGL().getGL2();
        
        gl.glClearColor(0.4f, 0.4f, 0.4f, 1);
    }

    @Override
    public void dispose(GLAutoDrawable glad) {
       
    }

    @Override
    public void display(GLAutoDrawable glad) {
        GL2 gl = glad.getGL().getGL2();
        gl.glClear(GL.GL_COLOR_BUFFER_BIT);
        
        gl.glPolygonMode(GL_FRONT, GL_LINE);
        
        gl.glBegin(GL.GL_TRIANGLES);
            gl.glColor3f(1, 0, 0);
            gl.glVertex2f(0.0f, 0.0f);
            gl.glColor3f(0, 1, 0);
            gl.glVertex2f(1,0);
            gl.glColor3f(0,0,1);
            gl.glVertex2f(0.5f,1);
        gl.glEnd();
        
        gl.glPolygonMode(GL_FRONT, GL_FILL);
        
        
        
    }

    @Override
    public void reshape(GLAutoDrawable glad, int x, int y, int width, int height) {
        
    }

    
   
}
