package ex1_final;

import javax.media.opengl.GLAutoDrawable;
import javax.media.opengl.GLEventListener;
import static javax.media.opengl.GL2.*;
import javax.media.opengl.GL2;

/**
 *
 * @author Milos Seleceni
 */
public class Scene implements GLEventListener {

    @Override
    public void init(GLAutoDrawable drawable)
    {
        // Ziskame si objekt GL2 ktory zpristupni funkcionalitu OpenGL
        GL2 gl = drawable.getGL().getGL2();
        
        // Nastavime farbu pozadia naseho okna
        gl.glClearColor(0.8f, 0.8f, 0.7f, 1.0f);
        
        // Zapneme linearne interpolovanie farieb
        gl.glShadeModel(GL_SMOOTH);
        
    }

    @Override
    public void dispose(GLAutoDrawable drawable)
    {
    }

    @Override
    public void display(GLAutoDrawable drawable)
    {
        GL2 gl = drawable.getGL().getGL2();
        
        /*
        *   Trojuholniky, ktore su predne,
        *   to su tie ktorych body su zadavane proti smeru hodinovych ruciciek,
        *   sa vykreslia plnou farbou
        *   alternativne mozete zmenit GL_LINE GL_POINT
        */
        gl.glPolygonMode(GL_FRONT, GL_FILL);
        
        /*
        *   Trojuholniky, ktore su zadne (v smere hodinovych ruciciek)
        *   sa budu vykreslovat len ako ciary
        *   alternativne mozete zmenit GL_LINE GL_POINT GL_FILL
        *   Ak pouzijet GL_POINT treba si dat este pozor na velkost bodu je nastavena na 1
        *   zmente aspon na 5
        */
        //  gl.glPointSize(5);
        gl.glPolygonMode(GL_BACK, GL_LINE);
        
        /*
        *   Premazeme nase pozadie farbou,
        *   ktoru sme nastavili glClearColor vo funkcii Init()
        */ 
        gl.glClear(GL_COLOR_BUFFER_BIT);
        
        
        // # Domcek 1
        /*gl.glBegin(GL_QUADS);
            
            gl.glColor3f(1.0f, 1.0f, 0);
        
            gl.glVertex2f(-0.4f, -0.6f);
            
            gl.glVertex2f(0.4f, -0.6f);
            
            gl.glVertex2f(0.4f, 0.2f);
            
            gl.glVertex2f(-0.4f, 0.2f);
            
        gl.glEnd();
        
        gl.glBegin(GL_TRIANGLES);
        
            gl.glColor3f(1.0f, 0.0f, 0);
            gl.glVertex2f(-0.4f, 0.2f);
            
            gl.glVertex2f(0.4f, 0.2f);
            
            gl.glVertex2f(0.0f, 0.6f);
            
        gl.glEnd();
        */
        // # Domcek 2
        // Iny sposob kreslenia toho isteho Domceku aj so strechou naraz
        //  Vsimnite si GL_TRIANGLE_FAN
        gl.glBegin(GL_TRIANGLE_FAN);
            
            gl.glColor3f(1.0f, 0.0f, 0);
            gl.glVertex2f(-0.4f, 0.2f);
            
            gl.glColor3f(1.0f, 1.0f, 0);
            gl.glVertex2f(-0.4f, -0.6f);
            gl.glVertex2f(0.4f, -0.6f);
            
            gl.glColor3f(1.0f, 0.0f, 0);
            gl.glVertex2f(0.4f, 0.2f);
            
            gl.glColor3f(0.2f, 0.0f, 0.0f);
            gl.glVertex2f(-0, 0.6f);
        
        gl.glEnd();
        
        /*
        *   Slnko 
        *   bodu su zadavane v smere hodin. ruciciek,
        *   preto je tento polygon oznaceny ako zadny podla OpenGL
        *   a vykresluje sa ako mnozina ciar
        */
        gl.glColor3f(1.0f, 1.0f, 0);
        gl.glBegin(GL_TRIANGLE_FAN);
            gl.glVertex2f(-0.80f, 0.80f); // Center
            gl.glVertex2f(-0.80f, 0.95f); // v1
            gl.glVertex2f(-0.77f, 0.87f); // v2
            gl.glVertex2f(-0.70f, 0.90f); // v3
            gl.glVertex2f(-0.73f, 0.83f); // v4
            gl.glVertex2f(-0.65f, 0.80f); // v5
            gl.glVertex2f(-0.73f, 0.77f); // v6
            gl.glVertex2f(-0.70f, 0.70f); // v7
            gl.glVertex2f(-0.77f, 0.73f); // v8
            gl.glVertex2f(-0.80f, 0.65f); // v9
            gl.glVertex2f(-0.83f, 0.73f); // v10
            gl.glVertex2f(-0.90f, 0.70f); // v11
            gl.glVertex2f(-0.87f, 0.77f); // v12
            gl.glVertex2f(-0.95f, 0.80f); // v13
            gl.glVertex2f(-0.87f, 0.83f); // v14
            gl.glVertex2f(-0.90f, 0.90f); // v15
            gl.glVertex2f(-0.83f, 0.87f); // v16
            gl.glVertex2f(-0.80f, 0.95f); // v1
        gl.glEnd();
        
        
        /*
        *   Plot
        *   Jednotlive body som zadaval proti smeru hodinovych ruciciek
        *   a preto budu vykreslene len ciary a nie plne stvoruholniky
        *   
        */
        drawFence(drawable);
        
        /*
        *   JOGL BUG
        *
        *   Tieto dve volania su potrebne len v kniznici JOGL
        *   ide o bug v kniznici a je potrebne vratit hodnoty 
        *   na povodne nastavenia, v opacnom pripade sa vykresli
        *   nezmyselna scena.
        *   Volania su potrebne len ak ste menili ich hodnoty,
        *   my sme zmenili aby sa zadne trojuholniky vykreslovali ako ciary a nie plnou farbou
        */
        gl.glPolygonMode(GL_FRONT, GL_FILL);
        gl.glPolygonMode(GL_BACK, GL_FILL);
        
    }

    @Override
    public void reshape(GLAutoDrawable drawable, int x, int y, int width, int height)
    {
    }
    
    private void drawFence(GLAutoDrawable drawable)
    {
        GL2 gl = drawable.getGL().getGL2();
        
        gl.glColor3f(0.0f, 0.4f, 0.0f);
        float offset = -0.9f;
        
        for(int i = 0; i < 10; i++)
        {
            gl.glBegin(GL_TRIANGLE_FAN);
                
                gl.glVertex2f(offset + 0.05f, -0.1f);
                gl.glVertex2f(offset + 0.05f, -0.8f);
                gl.glVertex2f(offset - 0.05f, -0.8f);    
                gl.glVertex2f(offset - 0.05f, -0.1f);
                gl.glVertex2f(offset + 0.0f, -0.05f);
                
            gl.glEnd();
            
            offset +=  0.2;
        }
    }

}
