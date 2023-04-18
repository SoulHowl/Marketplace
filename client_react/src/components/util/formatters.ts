import truncate from 'lodash/truncate';

const padTo2Digits = (num: number) => num.toString().padStart(2, '0');

export const dateToString = (date: Date | null): string =>
  date
    ? [
        padTo2Digits(date.getDate()),
        padTo2Digits(date.getMonth() + 1),
        date.getFullYear(),
      ].join('/')
    : '';

export const limitText = (text: string, length: number): string =>
  truncate(text, { length, omission: '...' });

const toArray = (rgb: number): [number, number, number] => {
  // eslint-disable-next-line no-bitwise
  const r = rgb >> 16;
  // eslint-disable-next-line no-bitwise
  const g = (rgb >> 8) % 256;
  const b = rgb % 256;

  return [r, g, b];
};

export const interpolateColor = (col1: string, col2: string, p: number) => {
  const rgb1 = parseInt(col1.replace('#', ''), 16);
  const rgb2 = parseInt(col2.replace('#', ''), 16);

  const [r1, g1, b1] = toArray(rgb1);
  const [r2, g2, b2] = toArray(rgb2);

  const q = 1 - p;
  const rr = Math.round(r1 * p + r2 * q);
  const rg = Math.round(g1 * p + g2 * q);
  const rb = Math.round(b1 * p + b2 * q);
  // eslint-disable-next-line no-bitwise
  return `#${Number((rr << 16) + (rg << 8) + rb)
    .toString(16)
    .padEnd(6, '0')}`;
};


