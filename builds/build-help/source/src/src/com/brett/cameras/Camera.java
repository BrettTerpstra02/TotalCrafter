package com.brett.cameras;

import java.nio.FloatBuffer;

import org.joml.Matrix4f;
import org.lwjgl.BufferUtils;

/**
 * View frustums help:
 * http://www8.cs.umu.se/kurser/5DV051/HT12/lab/plane_extraction.pdf
 * http://www.lighthouse3d.com/tutorials/view-frustum-culling/index/
 * 
 * ^ the code I made from those papers is now deleted and replaced with some dude's
 * frustum class, which I also don't use anymore.
 * 
 * @author brett
 * 
 * Camera base class.
 */
public class Camera extends ICamera {

	public Camera() {

	}

	@Override
	public void move() {
		
	}
	
	/**
	 * All of this plane stuff is taken from the frustum class.
	 */
	public float[][] m_Frustum = new float[6][4];
    public static final int RIGHT = 0;
    public static final int LEFT = 1;
    public static final int BOTTOM = 2;
    public static final int TOP = 3;
    public static final int BACK = 4;
    public static final int FRONT = 5;
    public static final int A = 0;
    public static final int B = 1;
    public static final int C = 2;
    public static final int D = 3;

	/**
	 * All of this plane stuff is taken from the frustum class.
	 */
    private FloatBuffer projbuff = BufferUtils.createFloatBuffer(16);
    private FloatBuffer modlbuff = BufferUtils.createFloatBuffer(16);
    private FloatBuffer clipbiff = BufferUtils.createFloatBuffer(16);
    float[] proj = new float[16];
    float[] modl = new float[16];
    float[] clippingPlanes = new float[16];

	/**
	 * All of this plane stuff is taken from the frustum class.
	 */
    private void normalizePlane(float[][] frustum, int side) {
            float nm = (float) Math.sqrt(frustum[side][0] * frustum[side][0] + frustum[side][1] * frustum[side][1] + frustum[side][2] * frustum[side][2]);

            frustum[side][0] /= nm;
            frustum[side][1] /= nm;
            frustum[side][2] /= nm;
            frustum[side][3] /= nm;
    }

	/**
	 * All of this plane stuff is taken from the frustum class.
	 * updates the frustum.
	 */
    public void calculateFrustum(Matrix4f projection, Matrix4f view) {
    		// clear the matrixs and store
            this.projbuff.clear();
            this.modlbuff.clear();
            this.clipbiff.clear();
            projection.set(this.projbuff);
            view.set(this.modlbuff);

            this.projbuff.flip().limit(16);
            this.projbuff.get(this.proj);
            this.modlbuff.flip().limit(16);
            this.modlbuff.get(this.modl);
            
            this.clippingPlanes[0] = (this.modl[0] * this.proj[0] + this.modl[1] * this.proj[4] + this.modl[2] * this.proj[8] + this.modl[3] * this.proj[12]);
            this.clippingPlanes[1] = (this.modl[0] * this.proj[1] + this.modl[1] * this.proj[5] + this.modl[2] * this.proj[9] + this.modl[3] * this.proj[13]);
            this.clippingPlanes[2] = (this.modl[0] * this.proj[2] + this.modl[1] * this.proj[6] + this.modl[2] * this.proj[10] + this.modl[3] * this.proj[14]);
            this.clippingPlanes[3] = (this.modl[0] * this.proj[3] + this.modl[1] * this.proj[7] + this.modl[2] * this.proj[11] + this.modl[3] * this.proj[15]);

            this.clippingPlanes[4] = (this.modl[4] * this.proj[0] + this.modl[5] * this.proj[4] + this.modl[6] * this.proj[8] + this.modl[7] * this.proj[12]);
            this.clippingPlanes[5] = (this.modl[4] * this.proj[1] + this.modl[5] * this.proj[5] + this.modl[6] * this.proj[9] + this.modl[7] * this.proj[13]);
            this.clippingPlanes[6] = (this.modl[4] * this.proj[2] + this.modl[5] * this.proj[6] + this.modl[6] * this.proj[10] + this.modl[7] * this.proj[14]);
            this.clippingPlanes[7] = (this.modl[4] * this.proj[3] + this.modl[5] * this.proj[7] + this.modl[6] * this.proj[11] + this.modl[7] * this.proj[15]);

            this.clippingPlanes[8] = (this.modl[8] * this.proj[0] + this.modl[9] * this.proj[4] + this.modl[10] * this.proj[8] + this.modl[11] * this.proj[12]);
            this.clippingPlanes[9] = (this.modl[8] * this.proj[1] + this.modl[9] * this.proj[5] + this.modl[10] * this.proj[9] + this.modl[11] * this.proj[13]);
            this.clippingPlanes[10] = (this.modl[8] * this.proj[2] + this.modl[9] * this.proj[6] + this.modl[10] * this.proj[10] + this.modl[11] * this.proj[14]);
            this.clippingPlanes[11] = (this.modl[8] * this.proj[3] + this.modl[9] * this.proj[7] + this.modl[10] * this.proj[11] + this.modl[11] * this.proj[15]);

            this.clippingPlanes[12] = (this.modl[12] * this.proj[0] + this.modl[13] * this.proj[4] + this.modl[14] * this.proj[8] + this.modl[15] * this.proj[12]);
            this.clippingPlanes[13] = (this.modl[12] * this.proj[1] + this.modl[13] * this.proj[5] + this.modl[14] * this.proj[9] + this.modl[15] * this.proj[13]);
            this.clippingPlanes[14] = (this.modl[12] * this.proj[2] + this.modl[13] * this.proj[6] + this.modl[14] * this.proj[10] + this.modl[15] * this.proj[14]);
            this.clippingPlanes[15] = (this.modl[12] * this.proj[3] + this.modl[13] * this.proj[7] + this.modl[14] * this.proj[11] + this.modl[15] * this.proj[15]);

            
            this.m_Frustum[0][0] = (this.clippingPlanes[3] - this.clippingPlanes[0]);
            this.m_Frustum[0][1] = (this.clippingPlanes[7] - this.clippingPlanes[4]);
            this.m_Frustum[0][2] = (this.clippingPlanes[11] - this.clippingPlanes[8]);
            this.m_Frustum[0][3] = (this.clippingPlanes[15] - this.clippingPlanes[12]);
            normalizePlane(this.m_Frustum, 0);

            this.m_Frustum[1][0] = (this.clippingPlanes[3] + this.clippingPlanes[0]);
            this.m_Frustum[1][1] = (this.clippingPlanes[7] + this.clippingPlanes[4]);
            this.m_Frustum[1][2] = (this.clippingPlanes[11] + this.clippingPlanes[8]);
            this.m_Frustum[1][3] = (this.clippingPlanes[15] + this.clippingPlanes[12]);

            normalizePlane(this.m_Frustum, 1);

            this.m_Frustum[2][0] = (this.clippingPlanes[3] + this.clippingPlanes[1]);
            this.m_Frustum[2][1] = (this.clippingPlanes[7] + this.clippingPlanes[5]);
            this.m_Frustum[2][2] = (this.clippingPlanes[11] + this.clippingPlanes[9]);
            this.m_Frustum[2][3] = (this.clippingPlanes[15] + this.clippingPlanes[13]);
            normalizePlane(this.m_Frustum, 2);

            
            this.m_Frustum[3][0] = (this.clippingPlanes[3] - this.clippingPlanes[1]);
            this.m_Frustum[3][1] = (this.clippingPlanes[7] - this.clippingPlanes[5]);
            this.m_Frustum[3][2] = (this.clippingPlanes[11] - this.clippingPlanes[9]);
            this.m_Frustum[3][3] = (this.clippingPlanes[15] - this.clippingPlanes[13]);
            normalizePlane(this.m_Frustum, 3);

            
            this.m_Frustum[4][0] = (this.clippingPlanes[3] - this.clippingPlanes[2]);
            this.m_Frustum[4][1] = (this.clippingPlanes[7] - this.clippingPlanes[6]);
            this.m_Frustum[4][2] = (this.clippingPlanes[11] - this.clippingPlanes[10]);
            this.m_Frustum[4][3] = (this.clippingPlanes[15] - this.clippingPlanes[14]);
            normalizePlane(this.m_Frustum, 4);

            
            this.m_Frustum[5][0] = (this.clippingPlanes[3] + this.clippingPlanes[2]);
            this.m_Frustum[5][1] = (this.clippingPlanes[7] + this.clippingPlanes[6]);
            this.m_Frustum[5][2] = (this.clippingPlanes[11] + this.clippingPlanes[10]);
            this.m_Frustum[5][3] = (this.clippingPlanes[15] + this.clippingPlanes[14]);
            normalizePlane(this.m_Frustum, 5);
    }

	/**
	 * All of this plane stuff is taken from the frustum class.
	 * finds if the point is in the frustum
	 */
	public boolean pointInFrustum(float x, float y, float z) {
		for (int i = 0; i < 6; i++) {
			if (this.m_Frustum[i][0] * x + this.m_Frustum[i][1] * y + this.m_Frustum[i][2] * z + this.m_Frustum[i][3] <= 0.0F) {
				return false;
			}
		}

		return true;
	}

	/**
	 * All of this plane stuff is taken from the frustum class.
	 * finds if the sphere is in the frustum
	 */
	public boolean sphereInFrustum(float x, float y, float z, float radius) {
		for (int i = 0; i < 6; i++) {
			if (this.m_Frustum[i][0] * x + this.m_Frustum[i][1] * y + this.m_Frustum[i][2] * z + this.m_Frustum[i][3] <= -radius) {
				return false;
			}
		}

		return true;
	}

	/**
	 * All of this plane stuff is taken from the frustum class.
	 * finds if this cube is in the frustum
	 */
	public boolean cubeInFrustum(float x1, float y1, float z1, float x2, float y2, float z2) {
		for (int i = 0; i < 6; i++) {
			if ((this.m_Frustum[i][0] * x1 + this.m_Frustum[i][1] * y1 + this.m_Frustum[i][2] * z1 + this.m_Frustum[i][3] <= 0.0F)
					&& (this.m_Frustum[i][0] * x2 + this.m_Frustum[i][1] * y1 + this.m_Frustum[i][2] * z1 + this.m_Frustum[i][3] <= 0.0F)
					&& (this.m_Frustum[i][0] * x1 + this.m_Frustum[i][1] * y2 + this.m_Frustum[i][2] * z1 + this.m_Frustum[i][3] <= 0.0F)
					&& (this.m_Frustum[i][0] * x2 + this.m_Frustum[i][1] * y2 + this.m_Frustum[i][2] * z1 + this.m_Frustum[i][3] <= 0.0F)
					&& (this.m_Frustum[i][0] * x1 + this.m_Frustum[i][1] * y1 + this.m_Frustum[i][2] * z2 + this.m_Frustum[i][3] <= 0.0F)
					&& (this.m_Frustum[i][0] * x2 + this.m_Frustum[i][1] * y1 + this.m_Frustum[i][2] * z2 + this.m_Frustum[i][3] <= 0.0F)
					&& (this.m_Frustum[i][0] * x1 + this.m_Frustum[i][1] * y2 + this.m_Frustum[i][2] * z2 + this.m_Frustum[i][3] <= 0.0F)
					&& (this.m_Frustum[i][0] * x2 + this.m_Frustum[i][1] * y2 + this.m_Frustum[i][2] * z2 + this.m_Frustum[i][3] <= 0.0F)) {
				return false;
			}
		}
		return true;
	}

}
