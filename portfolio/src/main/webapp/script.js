// Copyright 2020 Google LLC
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
//     https://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.

async function showServerMessage() {
  const messageContainer = document.getElementById('server-message');
  // clear previous message & image (if any)
  while (messageContainer.firstChild) {
    messageContainer.removeChild(messageContainer.firstChild);
  }
  // sends GET request to HelloWorldServlet & waits for array of responses
  const response = await fetch('/hello');
  const responseList = await response.json();
  // choose message at random index
  const responseText = responseList[Math.floor(Math.random() * responseList.length)];

  // set image source based on message
  let imgSrc;
  switch (responseText) {
    case "Painting":
      imgSrc = "/images/painting.png";
      break;
    case "Biking around Davis":
      imgSrc = "/images/bike.jpeg";
      break;
    case "My plants":
      imgSrc = "/images/plants.jpeg";
      break;
    case "Eating with friends":
      imgSrc = "/images/food.jpeg";
      break;
    case "Walking in nature":
      imgSrc = "/images/nature.jpeg";
      break;
    case "Church community":
      imgSrc = "/images/church.jpeg";
      break;
    case "Good conversations":
      imgSrc = "/images/conversations.jpeg";
      break;
    case "Family":
      imgSrc = "/images/family.jpeg";
      break;
  }
  // display message on fun.html
  const para = document.createElement("p");
  const text = document.createTextNode(responseText);
  para.appendChild(text);
  messageContainer.appendChild(para);
  // display corresponding image
  let img = document.createElement("img");
  img.src = imgSrc;
  img.id = "random-img"
  messageContainer.appendChild(img);
}

function thanksMessage() {
  const params = (new URL(document.location)).searchParams;
  const name = params.get("name");
  const messageContainer = document.getElementById('thanks-message');
  messageContainer.innerText = "Thank you, " + name + "! Your message was received.";
}
/*
async function submitHandler(formData) {
  const params = new FormData(formData);
  const response = await fetch('/form-handler', {method:'POST', body: params});
  console.log(response);
  const data = await response.json();
  document.getElementById('response').innerText = data;
}*/