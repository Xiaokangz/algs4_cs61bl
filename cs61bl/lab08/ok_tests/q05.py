
test = {
  'name': 'q05',
  'points': 1,
  'suites': [
    {
      'cases': [
        {
'code': r"""
>>> len(connection.execute(q5).fetchall()) == 9
True
>>> names = ['Sergey Brin', 'Mike Olson', 'Susan Wojcicki', 'Marissa Meyer', 'Cheryl Sandberg', 'Danah Boyd', 'Hillary Mason', 'Bill Gates', 'Mark Zuckerberg']
>>> [name for name, grade in connection.execute(q5).fetchall()] == names
True
>>> grades = [94.42, 91.88, 89.12, 88.55, 88.2, 88.13, 85.97, 85.83, 83.55]
>>> np.allclose([grade for name, grade in connection.execute(q5).fetchall()], grades)
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
