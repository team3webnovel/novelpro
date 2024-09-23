<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Image Generation Form</title>
    <style>
    #overlay {
	    position: fixed;
	    top: 0;
	    left: 0;
	    width: 100%;
	    height: 100%;
	    background: rgba(0, 0, 0, 0.5);
	    display: none; /* 기본적으로 숨김 */
	    z-index: 1000; /* 다른 요소 위에 표시 */
	}
    </style>
</head>
<body>
	<div id="overlay">Loading...</div>
	<!-- Model Checkpoint -->
	<label for="model_checkpoint">Model Checkpoint:</label>
	<select id="model_checkpoint" name="model_checkpoint">
	    <c:forEach var="model" items="${models}">
			<option value="${model}">${model}</option>
		</c:forEach>
	</select>
	<br><br>
    <h1>Create Image</h1>

    <form id="imageForm">
        <!-- Sampler Index -->
        <label for="sampler_index">Sampler Index:</label>
        <select id="sampler_index" name="sampler_index">
            <option value="Euler">Euler</option>
            <option value="Euler a">Euler a</option>
            <option value="DPM++ 2M">DPM++ 2M</option>
            <option value="DPM++ 2M SDE">DPM++ 2M SDE</option>
            <option value="IPNDM_V">IPNDM_V</option>
        </select>
        <br><br>

        <!-- Prompt -->
        <label for="prompt">Prompt:</label>
        <input type="text" id="prompt" name="prompt" required>
        <br><br>

        <!-- Negative Prompt -->
        <label for="negative_prompt">Negative Prompt:</label>
        <input type="text" id="negative_prompt" name="negative_prompt">
        <br><br>

        <!-- Steps -->
		<label for="steps">Steps (1-150):</label>
		<input type="range" id="stepsRange" name="stepsRange" min="1" max="150" value="30" oninput="document.getElementById('steps').value=this.value">
		<input type="number" id="steps" name="steps" min="1" max="150" value="30" oninput="document.getElementById('stepsRange').value=this.value">
		<br><br>

        <!-- Width -->
		<label for="width">Width (64-2048):</label>
		<input type="range" id="widthRange" name="widthRange" min="64" max="2048" value="896" oninput="document.getElementById('width').value=this.value">
		<input type="number" id="width" name="width" min="64" max="2048" value="896" oninput="document.getElementById('widthRange').value=this.value">
		<br><br>

        <!-- Height -->
		<label for="height">Height (64-2048):</label>
		<input type="range" id="heightRange" name="heightRange" min="64" max="2048" value="1152" oninput="document.getElementById('height').value=this.value">
		<input type="number" id="height" name="height" min="64" max="2048" value="1152" oninput="document.getElementById('heightRange').value=this.value">
		<br><br>

        <!-- CFG Scale -->
		<label for="cfg_scale">CFG Scale (1-20):</label>
		<input type="range" id="cfgScaleRange" name="cfgScaleRange" min="1" max="20" value="7" oninput="document.getElementById('cfg_scale').value=this.value">
		<input type="number" id="cfg_scale" name="cfg_scale" min="1" max="20" value="7" oninput="document.getElementById('cfgScaleRange').value=this.value">
		<br><br>

        <!-- Seed -->
        <label for="seed">Seed (-1 for random):</label>
        <input type="number" id="seed" name="seed" value="-1">
        <br><br>

        <!-- Enable High Resolution -->
        <label for="enable_hr">Enable High Resolution:</label>
        <input type="checkbox" id="enable_hr" name="enable_hr">
        <br><br>

        <!-- High Resolution Options (conditional fields) -->
        <div id="hr_options" style="display:none;">
            <!-- Denoising Strength -->
		    <label for="denoising_strength">Denoising Strength (0-1):</label>
		    <input type="range" id="denoisingStrengthRange" name="denoisingStrengthRange" min="0" max="1" step="0.01" value="0.9" oninput="document.getElementById('denoising_strength').value=this.value">
		    <input type="number" id="denoising_strength" name="denoising_strength" min="0" max="1" step="0.01" value="0.9" oninput="document.getElementById('denoisingStrengthRange').value=this.value">
		    <br><br>

		    <!-- HR Scale -->
		    <label for="hr_scale">HR Scale (1-4):</label>
		    <input type="range" id="hrScaleRange" name="hrScaleRange" min="1" max="4" step="0.05" value="1.3" oninput="document.getElementById('hr_scale').value=this.value">
		    <input type="number" id="hr_scale" name="hr_scale" min="1" max="4" step="0.05" value="1.3" oninput="document.getElementById('hrScaleRange').value=this.value">
		    <br><br>

            <!-- HR Upscaler (fixed value) -->
            <label for="hr_upscaler">HR Upscaler:</label>
            <select id="hr_upscaler" name="hr_upscaler">
	            <option value="R-ESRGAN 4x+">R-ESRGAN 4x+</option>
	            <option value="ESRGAN_4x">ESRGAN_4x</option>
	            <option value="DAT x2">DAT x2</option>
	            <option value="R-ESRGAN 4x+ Anime6B">R-ESRGAN 4x+ Anime6B</option>
	            <option value="SwinIR_4x">SwinIR_4x</option>
	            <option value="Latent">Latent</option>
        	</select>
            <br><br>
        </div>

        <!-- Submit Button -->
        <div id="loading" style="display:none;">Loading...</div>
		<button type="submit" id="submitButton">Submit</button>
    </form>

    <div id="result"></div>

	<script src="<%= request.getContextPath()%>/static/js/create_tool.js"></script>
</body>
</html>
