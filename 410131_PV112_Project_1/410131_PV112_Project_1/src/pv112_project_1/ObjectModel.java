/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package pv112_project_1;

import java.util.List;
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
        gl.glBegin(GL_TRIANGLES);
        
        for (int i = 0; i<vertexIndices.size(); i++) {
            for (int j =0; j<3; j++) {
                gl.glNormal3fv(normals.get((normalIndices.get(i))[j]), 0);
                gl.glTexCoord3fv(textures.get((textureIndices.get(i))[j]), 0);
                gl.glVertex3fv(vertices.get((vertexIndices.get(i))[j]), 0);
            }
        }

        gl.glEnd();
    }
}
