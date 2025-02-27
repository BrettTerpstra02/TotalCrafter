package com.brett.renderer.font.fontRendering;

import java.util.List;
import java.util.Map;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;
import com.brett.renderer.font.FontType;
import com.brett.renderer.font.UIText;

public class FontRenderer {

	private FontShader shader;
	
	public FontRenderer() {
		shader = new FontShader();
	}
	
	/**
	 * Renders all the texts supplied.
	 */
	public void render(Map<FontType, List<UIText>> texts){
		prepare();
		// batched font rendering
		for(FontType font : texts.keySet()){
			// bind the texture atlas for this font
			GL13.glActiveTexture(GL13.GL_TEXTURE0);
			GL11.glBindTexture(GL11.GL_TEXTURE_2D, font.getTextureAtlas());
			// render all the texts using this texture
			for(UIText text : texts.get(font)){
				renderText(text);
			}
		}
		endRendering();
	}

	public void cleanUp(){
		shader.cleanUp();
	}
	
	/**
	 * prepares OpenGL for font rendering
	 */
	public void prepare(){
		// enable blending or else font would create blank patches on the screen.
		GL11.glEnable(GL11.GL_BLEND);
		GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
		// make sure we don't try to do any depth stuff with this. Font is 2d.
		GL11.glDisable(GL11.GL_DEPTH_TEST);
		shader.start();
	}
	
	/**
	 * pretty standard renderer that takes in a GUIText and renders it
	 */
	private void renderText(UIText text){
		// this stuff should be like standard by now. Not much really changes for how things are rendered
		// bind the text VAO
		GL30.glBindVertexArray(text.getMesh());
		// enable the VBOs
		GL20.glEnableVertexAttribArray(0);
		GL20.glEnableVertexAttribArray(1);
		
		shader.loadColor(text.getColor());
		shader.loadColorOutline(text.getColorOutline());
		shader.loadTranslation(text.getPosition());
		
		// draw the text quads
		GL11.glDrawArrays(GL11.GL_TRIANGLES, 0, text.getVertexCount());
		
		// done drawing, disable.
		GL20.glDisableVertexAttribArray(0);
		GL20.glDisableVertexAttribArray(1);
		GL30.glBindVertexArray(0);
	}
	
	public void endRendering(){
		shader.stop();
		GL11.glDisable(GL11.GL_BLEND);
		GL11.glEnable(GL11.GL_DEPTH_TEST);
	}

}
