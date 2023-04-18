import { Group, Title } from '@mantine/core';
import React from 'react';
import { interpolateColor } from '../util/formatters';



type Props = { score: number | null; size: string };

const OverallScoreIndicator: React.FC<Props> = ({ score, size }) => (
  <Group
    align="center"
    position="center"
    sx={{
      backgroundColor: score
        ? interpolateColor('00FF00', 'FF0000', score / 10.0)
        : '#ADB5BD',
      borderRadius: '20px',
      height: size,
      minHeight: size,
      minWidth: size,
      width: size,
    }}
  >
    <Title order={1}>{score ? score.toFixed(1) : 'N/A'}</Title>
  </Group>
);

export default OverallScoreIndicator;