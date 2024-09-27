<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Generate Image</title>
</head>
<body>
    <h1>Generate Image from Prompt</h1>
    <form action="${pageContext.request.contextPath}/images/generate" method="post">
    
    	<!-- Sampler Index -->
        <label for="checkpoint">checkpoint:</label>
        <select id="checkpoint" name="checkpoint">
            <option value="aamXLAnimeMix_v10.safetensors">aamXLAnimeMix_v10</option>
            <option value="animagineXLV31_v31.safetensors">animagineXLV31_v31</option>
            <option value="juggernautXL_juggXIByRundiffusion.safetensors">juggernautXL_juggXIByRundiffusion</option>
            <option value="prefectPonyXL_v3.safetensors">prefectPonyXL_v3</option>
            <option value="romanticprism_v10.safetensors">romanticprism_v10</option>
            <option value="dreamshaper_8.safetensors">dreamshaper_8</option>
            <option value="majicmixRealistic.safetensors">majicmixRealistic</option>
            <option value="sdxlNijiSeven_sdxlNijiSeven.safetensors">sdxlNijiSeven_sdxlNijiSeven</option>
        </select>
        <br><br>
        
        <!-- Sampler Index -->
        <label for="sampler_index">Sampler Index:</label>
        <select id="sampler_index" name="sampler_index">
            <option value="euler">Euler</option>
            <option value="euler_ancestral">Euler a</option>
            <option value="dpmpp_2m">DPM++ 2M</option>
            <option value="dpmpp_2m_sde">DPM++ 2M SDE</option>
            <option value="ipndm_v">IPNDM_V</option>
            <option value="lms">LMS</option>
            <option value="lcm">LCM</option>
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

	    <!-- Seed 입력 -->
	    <label for="seed">Seed:</label>
	    <input type="number" id="seed" name="seed" value="1">
	    
	    <!-- 랜덤 Seed 버튼 -->
	    <button type="button" onclick="setRandomSeed()">랜덤 Seed</button>
	    
	    <br><br>

        <input type="submit" value="Generate Image">
    </form>
    
    <script>
        // 랜덤 Seed를 설정하는 함수
        function setRandomSeed() {
            // 랜덤 정수 생성 (예: 1부터 9999999까지)
            const randomSeed = Math.floor(Math.random() * 9999999) + 1;
            
            // seed 입력 필드에 랜덤 값을 설정
            document.getElementById('seed').value = randomSeed;
        }
    </script>
</body>
</html>

