package ex3;

import static javax.media.opengl.GL2.*;
import javax.media.opengl.GL2;
import javax.media.opengl.GLAutoDrawable;
import javax.media.opengl.GLEventListener;
import com.jogamp.opengl.util.gl2.GLUT;
import static javax.media.opengl.GL.GL_COLOR_BUFFER_BIT;
import static javax.media.opengl.GL.GL_DEPTH_BUFFER_BIT;
import static javax.media.opengl.GL.GL_FRONT_AND_BACK;
import static javax.media.opengl.GL2GL3.GL_LINE;
import javax.media.opengl.glu.GLU;

/**
 *
 * @author milos_000
 */
public class Scene implements GLEventListener
{
    private GLU glu;
    private GLUT glut;
    private ObjLoader model;
    
    private float time;
    private float amplitude;
    private float radius;
    private float azimuth;
    private float altitude;
    private float[] cartesian;
    
    public Scene()
    {
        cartesian = new float[3];
        time = 0;
        amplitude = 2;
        radius = 4;
        azimuth = 0;
        altitude = 0;
    }
            
    
    @Override
    public void init(GLAutoDrawable drawable)
    {
        GL2 gl = drawable.getGL().getGL2();
        
        glu = new GLU();
        glut = new GLUT();
        gl.glClearColor(.6f,.6f,.6f,1);
        
        // TODO LIGHTS
    
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
        gl.glLoadIdentity();        
        gl.glPolygonMode(GL_FRONT_AND_BACK, GL_LINE);
        
        //  Camera
        polarCoordinates(azimuth, altitude, cartesian);
        glu.gluLookAt(cartesian[0], cartesian[1], cartesian[2], 0, 0, 0, 0, 1, 0);
        
        // Draw Floor
        drawFloor(gl, -10, 10, -10, 10, 1f, 1f);
        
        //  Draw obj
        float move = amplitude * (float)Math.cos(2 * Math.PI * time);
        gl.glRotatef(time * 45, 0, 1, 0);
        gl.glTranslatef(radius, amplitude + 1 + move, 0);
        glut.glutSolidTeapot(1f);
        
        gl.glPolygonMode(GL_FRONT_AND_BACK, GL_FILL);
    }

    @Override
    public void reshape(GLAutoDrawable drawable, int x, int y, int width, int height)
    {
        GL2 gl = drawable.getGL().getGL2();
        
        gl.glMatrixMode(GL_PROJECTION);
        gl.glLoadIdentity();
        //gl.glOrthof(-10, 10, -10, 10, 0.1f, 100);
        glu.gluPerspective(55, (float)width/height, 1, 100);

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
    
    private void polarCoordinates(float azimuth, float altitude, float[] cartesian)
    {
        float a = (float) (15 * Math.cos(Math.toRadians(altitude)));
        cartesian[0] = (float) (a * Math.cos(Math.toRadians(azimuth)));
        cartesian[1] = (float) (15 * Math.sin(Math.toRadians(altitude)));
        cartesian[2] = (float) (a * Math.sin(Math.toRadians(azimuth)));
    }
    
    public void setAzimuth(float val)
    {
        this.azimuth = val;
    }
    
    public void setAltitude(float val)
    {
        this.altitude = val;
    }
    
}
