# -*- coding: utf-8 -*-
import pandas as pd
from faker import Faker
from typing import List, Dict
from tqdm import tqdm

def CREATE_DATA(RUNS: int, ignore_multi_value: bool = True):
    MyFaker: Faker = Faker('en-US')
    function: List[str] = [caller for caller in dir(MyFaker) if not caller.startswith('_')]

    ValidKey = []
    for func in function:
        try:
            res = MyFaker.__getattr__(func)
            if ignore_multi_value and isinstance(res, (list, tuple, dict)):
                continue
            ValidKey.append(func)
            print('Function:', func)
        except (ValueError, AttributeError):
            pass

    ITEM = [('Address', 'address'), ('Country', 'country'), ('Date', 'date'),
            ('Email', 'email'), ('Job', 'job'), ('Prefix', 'prefix'),
            ('First Name', 'first_name'), ('Last Name', 'last_name'),
            ('SSN', 'ssn')]

    COLUMN = {item[0]: [] for item in ITEM}
    COLUMN['Sex'] = []
    COLUMN['Blood'] = []
    COLUMN['Phone'] = []
    for _ in tqdm(range(RUNS)):
        for col, func in ITEM:
            try:
                res: str = MyFaker.__getattr__(func)().replace("\\n", ', ').replace(r'\n', ', ')
                res = ", ".join(r for r in res.splitlines() if r)
            except (ValueError, AttributeError):
                res = ""
            COLUMN[col].append(res)
        result: Dict = MyFaker.profile()
        COLUMN['Sex'].append('Female' if result['sex'] == 'F' else 'Male')
        COLUMN['Blood'].append(result['blood_group'])
        COLUMN['Phone'].append(MyFaker.__getattr__('msisdn')()[3:])

    return pd.DataFrame(COLUMN, index=None)


df = CREATE_DATA(RUNS=150000, ignore_multi_value=True)
print(df.keys())
print(df.head())
df.to_csv(f'patient-0-150000.csv', index=False)
