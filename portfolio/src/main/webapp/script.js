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
  // sends GET request to HelloWorldServlet & waits for array of responses
  const response = await fetch('/hello');
  const responseList = await response.json();
  // choose message at random index
  const responseText = responseList[Math.floor(Math.random() * responseList.length)];
  // update index.html with server message
  const messageContainer = document.getElementById('server-message');
  messageContainer.innerText = responseText;
}

async function submitHandler(formData) {
  const params = new FormData(formData);
  const response = await fetch('/form-handler', {method:'POST', body: params});
  console.log(response);
  const data = await response.json();
  document.getElementById('response').innerText = data;
}