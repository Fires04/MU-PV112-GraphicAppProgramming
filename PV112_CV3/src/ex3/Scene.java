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
import static javax.media.opengl.fixedfunc.GLLightingFunc.GL_DIFFUSE;
import static javax.media.opengl.fixedfunc.GLLightingFunc.GL_LIGHT0;
import static javax.media.opengl.fixedfunc.GLLightingFunc.GL_POSITION;
import static javax.media.opengl.fixedfunc.GLLightingFunc.GL_SPECULAR;
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
        
        
        gl.glEnable(GL_LIGHTING);//zapne svetla
        gl.glEnable(GL_LIGHT0);//zapne svetlo 0
        gl.glEnable(GL_LIGHT1);//zapne svetlo 1
        
        gl.glEnable(GL_DEPTH_TEST); //test depth bufferu
        
        glu = new GLU();
        glut = new GLUT();
        gl.glClearColor(.6f,.6f,.6f,1);
        gl.glClearDepthf(1.0f); //vsechny body budou nastaveny na vzdalenost 1 od kamery 
        
        gl.glLightfv(GL_LIGHT0, GL_DIFFUSE, new float[]{1,0,0},0);
        gl.glLightfv(GL_LIGHT0, GL_SPECULAR, new float[]{1,0,0},0);
        
        gl.glLightfv(GL_LIGHT1, GL_DIFFUSE, new float[]{0,1,0},0);
        gl.glLightfv(GL_LIGHT1, GL_SPECULAR, new float[]{0,1,0},0);
        
        
        gl.glMaterialfv(GL_FRONT_AND_BACK, GL_DIFFUSE, new float[]{1,1,1},0);
        gl.glMaterialfv(GL_FRONT_AND_BACK, GL_SPECULAR, new float[]{1,1,1},0);
        gl.glMaterialf(GL_FRONT_AND_BACK, GL_SHININESS, 100); // teoreticky rozptyl odrazu
        
    
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
        //zakomentovano aby jsme videli materialy
       // gl.glPolygonMode(GL_FRONT_AND_BACK, GL_LINE);
        
        //  Camera
        polarCoordinates(azimuth, altitude, cartesian);
        glu.gluLookAt(cartesian[0], cartesian[1], cartesian[2], 0, 0, 0, 0, 1, 0);
        
        //svetlo 0
        //pridani svetla a bodu do svetla + animace svetla podle konstanty time
        gl.glPushMatrix();
            gl.glRotatef(time*20, 0, 1, 0);
            gl.glTranslatef(4, 4, 4);
            gl.glLightfv(GL_LIGHT0, GL_POSITION, new float[]{0,0,0,1},0);

            gl.glTranslatef(4, 4, 4);
            glut.glutSolidSphere(0.2f, 10, 10);

        gl.glPopMatrix();
        
        //svetlo 1
         //pridani svetla a bodu do svetla + animace svetla podle konstanty time
        gl.glPushMatrix();
            gl.glTranslatef(4, 4, -4);
            gl.glLightfv(GL_LIGHT1, GL_POSITION, new float[]{0,0,0,1},0);

            gl.glTranslatef(4, 4, -4);
            glut.glutSolidSphere(0.2f, 10, 10);

        gl.glPopMatrix();
        
        // Draw Floor
        //svetlo se pocita pouze na vertexech , cim vice vertexu tim lepsi svetlo logicky
        drawFloor(gl, -10, 10, -10, 10, 0.1f, 0.1f);
        
        //  Draw obj
        float move = amplitude * (float)Math.cos(2 * Math.PI * time);
        gl.glRotatef(time * 45, 0, 1, 0);
        gl.glTranslatef(radius, amplitude + 1 + move, 0);
        glut.glutSolidTeapot(1f);
        
        ObjLoader ld = new ObjLoader("Terrain.obj");
        ld.load();
        
        
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
