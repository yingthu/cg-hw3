package cs4620.shape;

import java.util.HashMap;
import java.util.Map;

import javax.media.opengl.GL2;

public class Sphere extends TriangleMesh {

	public Sphere(GL2 gl) {
		super(gl);
	}


	@Override
	public void buildMesh(GL2 gl, float tolerance)
	{
		// TODO (Scene P2): Implement mesh generation for Sphere. Your code should
		// fill arrays of vertex positions/normals and vertex indices for triangles/lines
		// and put this information in the GL buffers using the
		//   set*()
		// methods from TriangleMesh.
		/*int latitudes = (int)Math.ceil(Math.PI / (tolerance/2.0));
		int longitudes = (int)Math.ceil(2.0*Math.PI / (tolerance/2.0));
		
		// Note that n intervals mean n+1 lines
		int vertexCount = (latitudes+1) * (longitudes+1);
		float[] vertices = new float[3*vertexCount];
		float[] normals = new float[3*vertexCount];
		float[] texCoords = new float[2*vertexCount];
		for (int i = 0; i <= latitudes; i++) {
			for (int j = 0; j <= longitudes; j++) {
				float theta = (float) (i * Math.PI / latitudes);
				float phi = (float) (j * 2.0 * Math.PI / longitudes);
				// Transform to xyz coordinates
				// It's a unit sphere
				float x = (float) (1.0*Math.sin(theta)*Math.cos(-phi));
				float z = (float) (1.0*Math.sin(-phi)*Math.sin(theta));
				float y = (float) (1.0*Math.cos(theta));
				// Index for current vertex
				int curIndex = j + longitudes * i;
				// Set vertex values
				vertices[3*curIndex] = x;
				vertices[3*curIndex+1] = y;
				vertices[3*curIndex+2] = z;
				// Set normals at vertex
				normals[3*curIndex] = x;
				normals[3*curIndex+1] = y;
				normals[3*curIndex+2] = z;
				// Set texCoords value
			
				texCoords[2*curIndex] = (float) (j*1.0/longitudes);
				texCoords[2*curIndex+1] = (float) (1.0-i*1.0/latitudes);
				
			}
		}
		// Set triangles
		int triangleCount = latitudes * longitudes * 2;
		int[] triangles = new int[triangleCount*3];
		int tNum = 0;
		for(int i = 0;i < latitudes;i++)
		{
			int nextI = (i + 1) % latitudes;
			for(int j = 0; j < longitudes; j++)
			{
				int nextJ = (j + 1) % longitudes;
				triangles[tNum] = i*longitudes + j;
				triangles[tNum + 1] = nextI*longitudes+j;
				triangles[tNum + 2] = i*longitudes+nextJ;
				
				triangles[tNum + 3] = i*longitudes+nextJ;
				triangles[tNum + 4] = nextI*longitudes+j;
				triangles[tNum + 5] = nextI*longitudes+nextJ;
				tNum += 6;
			}
		}
		// Set lines
		int lineCount = 2 * latitudes * longitudes;
		int[] lines = new int[lineCount*2];
		int toLong = 2 * latitudes * longitudes;
		
		for (int i = 0; i < latitudes; i++)
		{
			// Don't use % here to avoid unnecessary wires
			int nextI = i + 1;
			for (int j = 0; j < longitudes; j++)
			{
				int nextJ = (j + 1) % longitudes;
				// lines in longitudes direction
				lines[2*(j*latitudes+i)+0] = i * longitudes + j;
				lines[2*(j*latitudes+i)+1] = nextI * longitudes + j;
				// lines in latitudes direction
				lines[toLong+2*(i*longitudes+j)+0] = nextI * longitudes + j;
				lines[toLong+2*(i*longitudes+j)+1] = nextI * longitudes + nextJ;
			}
		}
		// Put into buffer
		setVertices(gl, vertices);
		setNormals(gl, normals);
		setTriangleIndices(gl, triangles);
		setWireframeIndices(gl, lines);
		// TODO (Shaders 2 P2): Generate texture coordinates for the sphere also.
		setTexCoords(gl, texCoords);*/
		// Half as many slices (horizontal cuts), since each slice divides the profile circle twice.
        int latitudes = (int) Math.ceil(Math.PI / (tolerance/2.0));
        int longitudes = (int) Math.ceil(2.0*Math.PI / (tolerance/2.0));

        // Create all the vertex data
        int size = (latitudes + 1) * (longitudes + 1);

        float[] vertices = new float[3*size];
        float[] normals = new float[3*size];
        float[] texCoords = new float[2*size];

        // Create vertices
        // Duplicate the poles segments-many times for simplicity.  
        int pos = 0;
        for (int lat = 0; lat <= latitudes; lat++) {
        	// Must duplicate the vertices on the wrapped edge to get the textures to come out right
            for (int longi = 0; longi <= longitudes; longi++) {
                		
            	// Radius = 1.0
                float x = (float) Math.cos(longi * 2 * Math.PI / longitudes) * (float) Math.sin(lat * Math.PI / latitudes);
                float y = (float) Math.cos(lat * Math.PI / latitudes);
                float z = (float) Math.sin(longi * 2 * Math.PI / longitudes) * (float) Math.sin(lat * Math.PI / latitudes);

                vertices[3*pos] = x;
                vertices[3*pos+1] = y;
                vertices[3*pos+2] = z;
                
                normals[3*pos] = x;
                normals[3*pos+1] = y;
                normals[3*pos+2] = z;
                
                texCoords[2*pos] = 1.0f - (longi / ((float) longitudes));
                texCoords[2*pos+1] = 1.0f - (lat / ((float) latitudes));
                pos++;
            }
        }

        // Set triangles
        int tris = 0;
        size = 2 * longitudes * latitudes;
        int[] triangles = new int[3*size];
        int topL, topR, botL, botR;
        for (int j = 0; j < latitudes; j++) {
        	for (int i = 0; i < longitudes; i++) {
        		topL = j * (longitudes + 1) + i;
                topR = topL + 1;
                botL = (j + 1) * (longitudes + 1) + i;
                botR = botL + 1;

                triangles[3*tris] = topL;
                triangles[3*tris+1] = topR;
                triangles[3*tris+2] = botL;
                tris++;
                triangles[3*tris] = botL;
                triangles[3*tris+1] = topR;
                triangles[3*tris+2] = botR;
                tris++;
        	}
        }
        
        // Set lines
        int lineCount = 2 * latitudes * longitudes;
 		int[] lines = new int[lineCount*2];
 		int toLong = 2 * latitudes * longitudes;
 		
        for (int j = 0; j < latitudes; j++) {
        	for (int i = 0; i < longitudes; i++) {
                topL = j * (longitudes + 1) + i;
                topR = topL + 1;
                botL = (j + 1) * (longitudes + 1) + i;
                lines[2*(i*latitudes+j)+0] = topL;
                lines[2*(i*latitudes+j)+1] = botL;
                lines[toLong+2*(j*longitudes+i)+0] = topL;
                lines[toLong+2*(j*longitudes+i)+1] = topR;
        	}
        }	
        setVertices(gl, vertices);
		setNormals(gl, normals);
		setTriangleIndices(gl, triangles);
		setWireframeIndices(gl, lines);
		// TODO (Shaders 2 P2): Generate texture coordinates for the sphere also.
		setTexCoords(gl, texCoords);
	}

	@Override
	public Object getYamlObjectRepresentation()
	{
		Map<Object,Object> result = new HashMap<Object, Object>();
		result.put("type", "Sphere");
		return result;
	}


}
