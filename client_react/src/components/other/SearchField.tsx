import { TextInput } from '@mantine/core';
import { useDebouncedState } from '@mantine/hooks';
import { IconSearch } from '@tabler/icons-react';
import React from 'react';
import { useEffect } from 'react';

const SearchField = ({
  setValue,
  refreshFetching
}: {
  setValue: (value: string) => void;
  refreshFetching:(value: boolean) => void;
}): JSX.Element => {
  const [value, setInternalValue] = useDebouncedState('', 1000);
  useEffect(() => {
    setValue(value);
    refreshFetching(true);
  }, [refreshFetching, setValue, value]);

  return (
    <TextInput
      defaultValue={value}
      icon={<IconSearch />}
      onChange={(event) => setInternalValue(event.currentTarget.value)}
      placeholder="Type something to search"
      radius="lg"
      size="xl"
      w="100%"
    />
  );
};

export default SearchField;
