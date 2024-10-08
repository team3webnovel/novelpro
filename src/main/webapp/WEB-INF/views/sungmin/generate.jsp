<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Generate Image</title>
    <!-- Bootstrap CSS -->
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
</head>
<body>
    <div class="container">
        <h1 class="mt-5 mb-4 text-center">Generate Image from Prompt</h1>
        <form action="${pageContext.request.contextPath}/images/generate" method="post">
            <div class="form-group">
                <label for="checkpoint">Checkpoint:</label>
                <select id="checkpoint" name="checkpoint" class="form-control">
                    <option value="aamXLAnimeMix_v10.safetensors">aamXLAnimeMix_v10</option>
                    <option value="animagineXLV31_v31.safetensors">animagineXLV31_v31</option>
                    <option value="juggernautXL_juggXIByRundiffusion.safetensors">juggernautXL_juggXIByRundiffusion</option>
                    <option value="prefectPonyXL_v3.safetensors">prefectPonyXL_v3</option>
                    <option value="romanticprism_v10.safetensors">romanticprism_v10</option>
                    <option value="dreamshaper_8.safetensors">dreamshaper_8</option>
                    <option value="majicmixRealistic.safetensors">majicmixRealistic</option>
                    <option value="sdxlNijiSeven_sdxlNijiSeven.safetensors">sdxlNijiSeven</option>
                </select>
            </div>

            <div class="form-group">
                <label for="sampler_index">Sampler Index:</label>
                <select id="sampler_index" name="sampler_index" class="form-control">
                    <option value="euler">Euler</option>
                    <option value="euler_ancestral">Euler a</option>
                    <option value="dpmpp_2m">DPM++ 2M</option>
                    <option value="dpmpp_2m_sde">DPM++ 2M SDE</option>
                    <option value="ipndm_v">IPNDM_V</option>
                    <option value="lms">LMS</option>
                    <option value="lcm">LCM</option>
                </select>
            </div>

            <div class="form-group">
                <label for="prompt">Prompt:</label>
				<textarea id="prompt" name="prompt" class="form-control" rows="4" required></textarea>
            </div>

            <div class="form-group">
                <label for="negative_prompt">Negative Prompt:</label>
                <textarea id="negative_prompt" name="negative_prompt" class="form-control" rows="4" required></textarea>
            </div>

            <div class="form-group">
                <label for="steps">Steps (1-100):</label>
                <input type="range" id="stepsRange" name="stepsRange" min="1" max="100" value="25" class="form-control-range" oninput="document.getElementById('steps').value=this.value">
                <input type="number" id="steps" name="steps" min="1" max="100" value="25" class="form-control mt-2" oninput="document.getElementById('stepsRange').value=this.value">
            </div>

            <div class="form-group">
                <label for="width">Width (64-2048):</label>
                <input type="range" id="widthRange" name="widthRange" min="64" max="2048" value="896" class="form-control-range" oninput="document.getElementById('width').value=this.value">
                <input type="number" id="width" name="width" min="64" max="2048" value="896" class="form-control mt-2" oninput="document.getElementById('widthRange').value=this.value">
            </div>

            <div class="form-group">
                <label for="height">Height (64-2048):</label>
                <input type="range" id="heightRange" name="heightRange" min="64" max="2048" value="1152" class="form-control-range" oninput="document.getElementById('height').value=this.value">
                <input type="number" id="height" name="height" min="64" max="2048" value="1152" class="form-control mt-2" oninput="document.getElementById('heightRange').value=this.value">
            </div>

            <div class="form-group">
                <label for="cfg_scale">CFG Scale (1-20):</label>
                <input type="range" id="cfgScaleRange" name="cfgScaleRange" min="1" max="20" value="7" class="form-control-range" oninput="document.getElementById('cfg_scale').value=this.value">
                <input type="number" id="cfg_scale" name="cfg_scale" min="1" max="20" value="7" class="form-control mt-2" oninput="document.getElementById('cfgScaleRange').value=this.value">
            </div>

            <div class="form-group">
                <label for="seed">Seed:</label>
                <input type="number" id="seed" name="seed" class="form-control" value="1">
                <button type="button" class="btn btn-secondary mt-2" onclick="setRandomSeed()">랜덤 Seed</button>
            </div>

            <button type="submit" class="btn btn-primary btn-block">Generate Image</button>
        </form>
    </div>

    <!-- Bootstrap JS and dependencies -->
    <script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.11.6/dist/umd/popper.min.js"></script>
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>

    <script>
        function setRandomSeed() {
            const randomSeed = Math.floor(Math.random() * 9999999) + 1;
            document.getElementById('seed').value = randomSeed;
        }
    </script>
</body>
</html>
