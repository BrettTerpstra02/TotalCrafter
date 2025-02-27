package com.brett.renderer.shaders;

import org.joml.Matrix4f;

import com.brett.renderer.ProjectionMatrix;

/**
* @author Brett
* @date Jun. 21, 2020
*/

public class WorldShader extends ShaderProgram {
	
	public int location_projectionMatrix;
	public int location_translationMatrix;
	public int location_viewMatrix;
	
	public WorldShader(String vertexFile, String fragmentFile) {
		super(vertexFile, fragmentFile);
		ProjectionMatrix.addShader(this);
	}

	@Override
	protected void getAllUniformLocations() {
		location_projectionMatrix = super.getUniformLocation("projectionMatrix");
		location_translationMatrix = super.getUniformLocation("translationMatrix");
		location_viewMatrix = super.getUniformLocation("viewMatrix");
	}

	@Override
	protected void bindAttributes() {
	}
	
	public void loadProjectionMatrix(Matrix4f matrix) {
		super.loadMatrix(location_projectionMatrix, matrix);
	}
	
	public void loadTranslationMatrix(Matrix4f matrix) {
		super.loadMatrix(location_translationMatrix, matrix);
	}
	
	public void loadTransformationMatrix(Matrix4f matrix){
		loadTranslationMatrix(matrix);
	}
	
	public void loadViewMatrix(Matrix4f matrix) {
		super.loadMatrix(location_viewMatrix, matrix);
	}

}
