package cs4620.manip;

import javax.vecmath.Matrix3f;
import javax.vecmath.Matrix4f;
import javax.vecmath.Tuple3f;
import javax.vecmath.Vector3f;
import javax.vecmath.Vector4f;

import cs4620.framework.Transforms;
import cs4620.scene.SceneNode;

/**
 * A place to store static utility functions for implementing the manipulators.
 */

public class ManipUtils {
	
	public static Matrix3f tempMatrix3f = new Matrix3f();
	public static Vector4f tempPosition4f = new Vector4f(0.0f, 0.0f, 0.0f, 1.0f);
	public static Vector4f tempVector4f = new Vector4f(0.0f, 0.0f, 0.0f, 0.0f);
	
	public static void applyNodeRotation(SceneNode node, Vector3f v, Vector3f outv)
	{
		if(outv != v)
			outv.set(v);

		// Rotate
		tempMatrix3f.rotX((float)Math.toRadians(node.rotation.x));
		tempMatrix3f.transform(outv);
		tempMatrix3f.rotY((float)Math.toRadians(node.rotation.y));
		tempMatrix3f.transform(outv);
		tempMatrix3f.rotZ((float)Math.toRadians(node.rotation.z));
		tempMatrix3f.transform(outv);
	}
	
	public static void applyNodeRotation(SceneNode node, Vector3f v)
	{
		// do in-place
		applyNodeRotation(node, v, v);
	}
	
	public static void transformPosition(Matrix4f mat, Tuple3f p, Tuple3f out)
	{
		tempPosition4f.set(p.x, p.y, p.z, 1.0f);
		mat.transform(tempPosition4f);
		out.set(tempPosition4f.x, tempPosition4f.y, tempPosition4f.z);
	}
	
	public static void transformPosition(Matrix4f mat, Tuple3f p)
	{
		// do transform in-place
		transformPosition(mat, p, p);
	}
	
	public static void transformVector(Matrix4f mat, Tuple3f v, Tuple3f out)
	{
		tempVector4f.set(v.x, v.y, v.z, 0.0f);
		mat.transform(tempVector4f);
		out.set(tempVector4f.x, tempVector4f.y, tempVector4f.z);
	}
	
	public static void transformVector(Matrix4f mat, Tuple3f v)
	{
		// do transform in-place
		transformVector(mat, v, v);
	}
	
	/**
	 * Get a vector not parallel to v.
	 */
	public static void nonParallelVector(Vector3f v, Vector3f nonParallel)
	{
		float absX = Math.abs(v.x);
		float absY = Math.abs(v.y);
		float absZ = Math.abs(v.z);
		
		if (absX <= absY && absX <= absZ)
			nonParallel.set(1,0,0);
		else if (absY <= absX && absY <= absZ)
			nonParallel.set(0,1,0);
		else
			nonParallel.set(0,0,1);
	}
	
	/**
	 * Returns some orthonormal matrix that rotates the z axis to v.
	 */
	
	public static Matrix4f rotateZTo(Vector3f v)
	{
		Vector3f orthoX = new Vector3f();
		Vector3f orthoY = new Vector3f();
		Vector3f orthoZ = new Vector3f(v);
		orthoZ.normalize();

		nonParallelVector(orthoZ, orthoX);
		
		orthoY.cross(orthoZ, orthoX);
		orthoX.cross(orthoY, orthoZ);
		orthoX.normalize();
		orthoY.normalize();
		
		return Transforms.fromColumns3DH(orthoX, orthoY, orthoZ);
	}
	
	/**
	 * Given a ray specified by point p and vector v and a target vector specified
	 * by pTarget and vTarget, find the time t along the ray p, v at which the
	 * ray comes closest to the target ray. Return some constant value if
	 * there is not a unique value of t.
	 */
	
	public static float timeClosestToRay(Vector3f p, Vector3f v, Vector3f pTarget, Vector3f vTarget)
	{
		return 0;
	}
	
	/**
	 * Return the time along the ray defined by p, v at which the ray
	 * intersects the plane defined by position planeP and normal planeN.
	 */
	
	public static float intersectRayPlane(Vector3f p, Vector3f v, Vector3f planeP, Vector3f planeN)
	{
		return 0;
	}

}
