window.swaggerSpec={
  "swagger" : "2.0",
  "info" : {
    "description" : "",
    "version" : "1.0.0",
    "title" : "Centurio",
    "contact" : {
      "email" : "ooctogene@gmail.com"
    }
  },
  "host" : "centurio-hm2021.herokuapp.com",
  "basePath" : "/",
  "tags" : [ {
    "name" : "cover",
    "description" : "Everything about covers"
  } ],
  "schemes" : [ "https", "http" ],
  "paths" : {
    "/token" : {
      "post" : {
        "tags" : [ "token" ],
        "summary" : "Token information",
        "description" : "",
        "consumes" : [ "application/json" ],
        "responses" : {
          "200" : {
            "description" : "Successful operation"
          }
        },
        "parameters" : [ {
          "in" : "body",
          "name" : "body",
          "description" : "Token with recommended covers",
          "required" : true,
          "schema" : {
            "$ref" : "#/definitions/TokenRequest"
          }
        } ]
      }
    },
    "/cover" : {
      "get" : {
        "tags" : [ "cover" ],
        "summary" : "Get all covers from Nexus Mutual",
        "description" : "",
        "produces" : [ "application/json" ],
        "responses" : {
          "200" : {
            "description" : "Successful operation",
            "schema" : {
              "$ref" : "#/definitions/Covers"
            }
          }
        }
      }
    },
    "/cover/recommend/{address}" : {
      "get" : {
        "tags" : [ "cover" ],
        "summary" : "Get recommended covers for a given wallet address",
        "produces" : [ "application/json" ],
        "parameters" : [ {
          "name" : "address",
          "in" : "path",
          "description" : "Address of a wallet",
          "required" : true,
          "type" : "string"
        } ],
        "responses" : {
          "200" : {
            "description" : "Successful operation",
            "schema" : {
              "$ref" : "#/definitions/Recommandations"
            }
          },
          "400" : {
            "description" : "Invalid Address supplied"
          }
        }
      }
    },
    "/user/{address}" : {
      "get" : {
        "tags" : [ "user" ],
        "summary" : "Get user",
        "produces" : [ "application/json" ],
        "parameters" : [ {
          "name" : "address",
          "in" : "path",
          "description" : "Address of a wallet",
          "required" : true,
          "type" : "string"
        } ],
        "responses" : {
          "200" : {
            "description" : "Successful operation",
            "schema" : {
              "$ref" : "#/definitions/User"
            }
          },
          "404" : {
            "description" : "User not registered"
          }
        }
      },
      "post" : {
        "tags" : [ "user" ],
        "summary" : "Create user",
        "produces" : [ "application/json" ],
        "parameters" : [ {
          "name" : "address",
          "in" : "path",
          "description" : "Address of a wallet",
          "required" : true,
          "type" : "string"
        } ],
        "responses" : {
          "200" : {
            "description" : "User registered"
          },
          "404" : {
            "description" : "User not registered"
          }
        }
      }
    },
    "/authentication" : {
      "post" : {
        "tags" : [ "authentication" ],
        "summary" : "Get JWT Token",
        "produces" : [ "application/text" ],
        "parameters" : [ {
          "in" : "body",
          "name" : "payload",
          "description" : "The data needed to authentify",
          "schema" : {
            "$ref" : "#/definitions/AuthenticationRequest"
          }
        } ],
        "responses" : {
          "200" : {
            "description" : "Successful operation"
          },
          "400" : {
            "description" : "Signature is not valid for the user"
          }
        }
      }
    }
  },
  "definitions" : {
    "Cover" : {
      "type" : "object",
      "properties" : {
        "name" : {
          "type" : "string",
          "description" : "contract's name"
        },
        "address" : {
          "type" : "string",
          "description" : "contract's address"
        },
        "type" : {
          "type" : "string",
          "description" : "cover type",
          "enum" : [ "protocol", "custodian", "yield" ]
        },
        "supportedChains" : {
          "type" : "array",
          "description" : "contract's chains supported",
          "items" : {
            "type" : "string",
            "enum" : [ "ethereum", "bsc", "fantom", "polygon", "starkware", "xdai", "terra", "thorchain" ]
          }
        },
        "logo" : {
          "type" : "string",
          "description" : "logo filename"
        }
      }
    },
    "Covers" : {
      "type" : "array",
      "items" : {
        "$ref" : "#/definitions/Cover"
      }
    },
    "TokenRequest" : {
      "type" : "object",
      "properties" : {
        "token" : {
          "type" : "string",
          "description" : "token address"
        },
        "covers" : {
          "type" : "array",
          "description" : "array of covers address",
          "items" : {
            "type" : "string"
          }
        }
      }
    },
    "Recommandations" : {
      "type" : "object",
      "properties" : {
        "count" : {
          "type" : "integer"
        },
        "recommandations" : {
          "type" : "array",
          "items" : {
            "$ref" : "#/definitions/Recommandation"
          }
        },
        "unsupportedTokens" : {
          "type" : "array",
          "items" : {
            "$ref" : "#/definitions/Token"
          }
        }
      }
    },
    "Token" : {
      "type" : "object",
      "properties" : {
        "name" : {
          "type" : "string"
        },
        "address" : {
          "type" : "string"
        },
        "symbol" : {
          "type" : "string"
        },
        "owner" : {
          "type" : "string"
        }
      }
    },
    "Recommandation" : {
      "type" : "object",
      "properties" : {
        "cover" : {
          "type" : "object",
          "$ref" : "#/definitions/Cover"
        },
        "reasoning" : {
          "type" : "array",
          "items" : {
            "type" : "object",
            "$ref" : "#/definitions/Reasoning"
          }
        }
      }
    },
    "Reasoning" : {
      "type" : "object",
      "properties" : {
        "token" : {
          "type" : "string",
          "description" : "Token symbol"
        },
        "logoUrl" : {
          "type" : "string",
          "description" : "Token logo url"
        },
        "description" : {
          "type" : "string",
          "description" : "Explaination"
        }
      }
    },
    "User" : {
      "type" : "object",
      "properties" : {
        "address" : {
          "type" : "string",
          "description" : "User address"
        },
        "nonce" : {
          "type" : "string",
          "description" : "Nonce used for signature"
        },
        "email" : {
          "type" : "string",
          "description" : "User's email"
        }
      }
    },
    "AuthenticationRequest" : {
      "type" : "object",
      "properties" : {
        "address" : {
          "type" : "string",
          "description" : "User address"
        },
        "signature" : {
          "type" : "string",
          "description" : "The result of the signature process"
        }
      }
    }
  }
}