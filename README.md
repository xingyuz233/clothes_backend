# Clothes_backend

* 注册（/auth/register）

  ```yaml
  Request: 
  	Haeder: {
  		"Content-Type":"application/json"
  	}
  	Body: {
  		"username":[your_username],
  		"password":[your_password],
  		"email":[your_email]
  	}
  
  // if exist
  Response: {
  	Status: 400,
  	Body: "User exists!"
  }
  // else {
  	Status: 200,
  	Body: {
  	  "access_token": [access_token],
      "token_type": "Bearer",
      "expires_in": 60480000,
      "refresh_token": [refresh_token],
      "created_at": [current_time]
  	}
  }
  ```

* 登陆（/auth/login）

  * Just test the api

* 刷新令牌（/auth/fresh）

  * Just test the api