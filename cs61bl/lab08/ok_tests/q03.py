
test = {
  'name': 'q03',
  'points': 1,
  'suites': [
    {
      'cases': [
        {
'code': r"""
>>> len(connection.execute(q3).fetchall()) == 63
True
>>> expected = [(0, 'Sergey Brin', 2.8, 40, 'CS', 'M', 1, 'hw', 0, 100.0), (1, 'Danah Boyd', 3.9, 35, 'CS', 'F', 29, 'labs', 1, 100.0), (2, 'Bill Gates', 1.0, 60, 'CS', 'M', 57, 'final', 2, 76.0), (4, 'Mike Olson', 3.7, 50, 'CS', 'M', 23, 'vitamins', 4, 95.0), (5, 'Mark Zuckerberg', 4.0, 30, 'CS', 'M', 51, 'midterm', 5, 86.0), (7, 'Susan Wojcicki', 4.0, 46, 'BUSINESS', 'F', 17, 'proj', 7, 82.0), (8, 'Marissa Meyer', 4.0, 45, 'BUSINESS', 'F', 45, 'participation', 8, 73.0)]
>>> sorted(connection.execute(q3).fetchall())[::10] == expected
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
