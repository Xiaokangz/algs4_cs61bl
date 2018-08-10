
test = {
  'name': 'q01',
  'points': 1,
  'suites': [
    {
      'cases': [
        {
'code': r"""
>>> len(connection.execute(q1).fetchall()) == 9
True
>>> connection.execute(q1).fetchall()[0] == (0, 'Sergey Brin', 2.8, 40, 'CS', 'M')
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
