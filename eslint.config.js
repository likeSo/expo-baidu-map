const { FlatCompat } = require('@eslint/eslintrc');

const compat = new FlatCompat({
  baseDirectory: __dirname,
});

module.exports = [
  ...compat.extends('universe/native', 'universe/web'),
  {
    ignores: ['build/**'],
    rules: {
      'node/handle-callback-err': 'off',
    },
  },
];
