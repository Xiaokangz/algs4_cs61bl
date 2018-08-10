
test = {
  'name': 'q02',
  'points': 1,
  'suites': [
    {
      'cases': [
        {
'code': r"""
>>> len(connection.execute(q2).fetchall()) == 1
True
>>> np.allclose(connection.execute(q2).fetchall()[0][0], 88.8730)
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
