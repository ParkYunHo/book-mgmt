function fn() {
  var config = {
    baseUrl: "http://127.0.0.1:8080",
  };

  karate.configure('connectTimeout', 50000);
  karate.configure('readTimeout', 50000);
  // karate.configure("ssl", false);
  // karate.configure("proxy", "http://127.0.0.1:8080")
  return config;
}