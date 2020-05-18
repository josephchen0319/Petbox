let profile_image = document.querySelector("input[name='profile_image']");
let image_holder = document.querySelector("#image_holder");
function showImg() {
	let file = this;
	let reader = new FileReader();
    reader.readAsDataURL(this.files[0]);
    console.log(this.files[0]);
    reader.addEventListener("load", function () {
    	image_holder.innerHTML = `<img width="100%" src="${reader.result}">`;
        
    })
}

profile_image.onchange = showImg;