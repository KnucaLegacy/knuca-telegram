module.exports = {
    "parser": "babel-eslint",
    "env": {
        "browser": true,
        "es6": true,
        "jest": true,
    },
    "settings": {
          "ecmascript": 6,
          "jsx": true
    },
    "parserOptions": {
        "ecmaVersion": 2017,
        "ecmaFeatures": {
            "experimentalObjectRestSpread": true,
            "experimentalDecorators": true,
            "jsx": true
        },
        "sourceType": "module"
    },
    "plugins": [
        "react",
    ],
    "extends": "airbnb",
    "rules": {
      "linebreak-style": ["error", "windows"],
      "react/jsx-indent-props": [2, 4],
      "react/jsx-filename-extension": 0,
      "function-paren-newline": 0,
      "class-methods-use-this": 0,
      "react/require-default-props": 0,
      "import/prefer-default-export": 0,
      "no-use-before-define": 0,
    }
  };