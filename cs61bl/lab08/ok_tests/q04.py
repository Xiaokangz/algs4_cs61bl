
test = {
  'name': 'q04',
  'points': 1,
  'suites': [
    {
      'cases': [
        {
'code': r"""
>>> len(connection.execute(q4).fetchall()) == 9
True
>>> names = ['Sergey Brin', 'Danah Boyd', 'Susan Wojcicki', 'Mike Olson', 'Cheryl Sandberg', 'Bill Gates', 'Marissa Meyer', 'Hillary Mason', 'Mark Zuckerberg']
>>> [name for name, grade in connection.execute(q4).fetchall()] == names
True
>>> grades = [93.5714285714286, 91.4285714285714, 89.8571428571429, 89.0, 88.8571428571429, 87.1428571428571, 87.0, 86.8571428571429, 86.1428571428571]
>>> np.allclose([grade for name, grade in connection.execute(q4).fetchall()], grades)
True

""",
          'hidden': False,
          'locked': False
        }
      ],
      'scored': True,
      'setup': '',
      'teardown': '',
      'type': 'doctest'
    }
  ]
}
