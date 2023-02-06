# Bug-Tracker
Bug tracker software built in java maven using SQL Server Express.

NOTES
1. There is only one administrator user. This user has the role administrator.
2. Each developer has his own user. To enter his respective app you must submit your login and password. There are multiple developers.
3. There are multiple QA analysts. They all have the same app (the QA analyst app).
4. Login authenticates the user and then sends him to his corresponding app depending on the role.


In this project three apps were created:

<h3>Software Developer's Manager App</h3>
<ol>
  <li>Make the CRUD for software projects and developers. Each software project has a team of developers.</li>
  <li>Keep track of bug statistics (Pie chart).
    <ul>
      <li>Number of new bugs</li>
      <li>Number of assigned bugs</li>
      <li>Number of finished bugs</li>
    </ul>
  </li>
  <li>Assign bugs to developers.
  <ul>
      <li>the bug belongs to a software project, then it must be assigned to a developer of this software project.</li>
      <li>Assign it to a developer of this software project.</li>
    </ul>
  </li>
  <li>Comment the bugs that have NOT yet been finalized.</li>
  <li>An email should be sent to the developer of the assigned bug.</li>
  <li> Notify new bug comments.</li>
</ol>

<h3>Software Developer's App</h3>
<ol>
  <li>Display an authentication window for the developer to log in to the app with their respective code.</li>
  <li>Show the software projects to which this developer belongs.</li>
  <li>Assign the bug fix start date</li>
  <li>Assign the bug fix start date</li>
  <li>Set the bugs as finished. Once the bug is finished, the current date is assigned as the bug completion date.</li>
  <li>Show a monthly calendar where the bug appears with its respective start date.</li>
  <li>Comments on bugs that have NOT yet been finalized.</li>
  <li>Notifies the new comments of the bugs that correspond to it.</li>
</ol>

<h3> QA Analyst App</h3>
<ol>
  <li>Generates the bugs for the different software projects.</li>
  <li>Displays a table with all assigned and finished bugs with their full information including the name of the complete information including the name of the developer who fixed the bug.
    <ul>
      <li>You can filter the bugs by the project code.</li>
      <li>You can filter the bugs by developer's name.</li>
    </ul>
  </li>
  <li>Creates a PDF report of all the bugs sorted first by the project name and by their project name and by their respective status.</li>
  <li>Comments on bugs that have NOT yet been finalized.</li>
  <li> Notify new bug comments.</li>
</ol>
