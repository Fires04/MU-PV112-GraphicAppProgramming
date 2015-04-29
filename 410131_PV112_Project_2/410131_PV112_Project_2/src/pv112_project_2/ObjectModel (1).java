/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package pv112_project_2;

import com.jogamp.opengl.util.texture.Texture;
import com.jogamp.opengl.util.texture.TextureData;
import com.jogamp.opengl.util.texture.TextureIO;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import static javax.media.opengl.GL.GL_TEXTURE_2D;
import static javax.media.opengl.GL.GL_TRIANGLES;
import javax.media.opengl.GL2;

/**
 *
 * @author Jakub Kremláček
 */

public class ObjectModel {
    private List<int[]> vertexIndices;
    private List<int[]> normalIndices;
    private List<int[]> textureIndices;
    private List<float[]> vertices;
    private List<float[]> normals;
    private List<float[]> textures;
    private Texture texture;

    public ObjectModel(String path) {
        ObjLoader Obj = new ObjLoader(path);
        Obj.load();
        vertexIndices = Obj.getVertexIndices();
        normalIndices = Obj.getNormalIndices();
        textureIndices = Obj.getTextureIndices();
        vertices = Obj.getVertices();
        normals = Obj.getNormals();
        textures = Obj.getTextures();
    }
    
    public void draw(GL2 gl) {
        //enable texture musi byt zavolano pred glBegin
        if (texture != null) {
            gl.glEnable(GL_TEXTURE_2D);
            texture.bind(gl);
            this.drawObj(gl);
            gl.glDisable(GL_TEXTURE_2D);
        } else {
            this.drawObj(gl);
        }
    }
    
    private void drawObj(GL2 gl) {
        gl.glBegin(GL_TRIANGLES);
        
        for (int i = 0; i<vertexIndices.size(); i++) {
            for (int j =0; j<3; j++) {
                gl.glNormal3fv(normals.get((normalIndices.get(i))[j]), 0);
                gl.glTexCoord2fv(textures.get((textureIndices.get(i))[j]), 0);
                gl.glVertex3fv(vertices.get((vertexIndices.get(i))[j]), 0);
            }
        }
        gl.glEnd();
    }

    public void loadTexture(GL2 gl, URL url, String suffix){
        Texture texture;
        try {
            TextureData data = TextureIO.newTextureData(gl.getGLProfile(), url, true, suffix);
            this.texture = new Texture(gl, data);
        } catch (IOException ex) {
            Logger.getLogger(OpenGlListener.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
