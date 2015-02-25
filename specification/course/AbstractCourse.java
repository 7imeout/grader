package course;

import admin.RoleManager;
import admin.Session;
import admin.User;
import assignment.Assignment;
import assignment.AssignmentCategory;
import assignment.AssignmentGrade;
import assignment.AssignmentSubmission;
import command.CommandHistory;
import user.student.StudentRecord;

import java.util.Collection;

/**
 * A Course consists of several fields necessary for class organization. The
 * RoleManager manages the access a user has to the class. The Name indicates
 * the offered name of a course within a catalog; this is
 * paired with a catalog Description of the course. There is also a GradeSchema
 * and LatePolicy associated with each course, both set by the professor.
 * <p/>
 * CommandTarget is implemented by Course to perform commands on a course spreadsheet,
 * and GraderData is implemented so that a user may add, modify, or remove grade data
 * to a spreadsheet.
 */
public abstract class AbstractCourse implements Course {
   /**
    * Manages user role assignments. Used by CommandTarget to authorize users
    * who issue commands.
    */
   public RoleManager roleManager;

   public Session current;

   /**
    * A GradeSchema contains a function to convert a raw score to a LetterGrade.
    * <p/>
    * This can be set by a professor to customize a grading scale. GraderData
    * is implemented to set a letter grade based on the raw score.
    */
   public GradeSchema gradeSchema;

   /**
    * A policy for calculating a grade penalty on late assignments.
    */
   public LatePolicy latePolicy;

   /**
    * Name of the course.
    */
   public String name;

   /**
    * A read only version of the course for students.
    */
   public CourseAccessor snapShot;

   /**
    * Data model for the grade book spreadsheet.
    */
   public Collection<StudentRecord> studentRecords;

   /**
    * All assignment categories in a course.
    */
   public Collection<AssignmentCategory> assignmentCategories;

   /**
    * All commands that have been delivered to this.
    */
   public CommandHistory commandHistory;

   /**
    * Accessor for the name of this <code>Course</code>.
    * @return name of this <code>Course</code>.
    * <pre>
    * pre:
    *    session != null &&
    *    session.currentUser != null &&
    *    roleManager.getPerms(session.currentUser).contains(
    *       Permission.ACCESS_COURSE_NAME)
    * post:
    *    name' == name
    */
   public abstract String getName();

   /**
    * Sets the name of this <code>Course</code>.
    * @param n desired name of this <code>Course</code>.
    * <p/>
    * <pre>
    * pre:
    *    session != null &&
    *    session.currentUser != null &&
    *    roleManager.getPerms(session.currentUser).contains(
    *       Permission.UPDATE_COURSE_NAME)
    * post:
    *    name' == n
    */
   public abstract void setName(String n);

   /**
    * Accessor fot the <code>GradeSchema</code> for this <code>Course</code>.
    * @return <code>GradeSchema</code> for this <code>Course</code>.
    * <p/>
    * <pre>
    * pre: session != null &&
    *    session.currentUser != null &&
    *    gradeSchema != null &&
    *    roleManager.getPerms(session.currentUser).contains(
    *       Permission.ACCESS_COURSE_GRADE_SCHEMA)
    * post:
    *    (gradeSchema' == gradeSchema)
    */
   public abstract GradeSchema getGradeSchema();

   /**
    * Sets the <code>GradeSchema</code> for this <code>Course</code>.
    * @param g desired <code>GradeSchema</code>.
    * <p/>
    * <pre>
    * pre:
    *    session != null &&
    *    session.currentUser != null &&
    *    roleManager.getPerms(session.currentUser).contains(
    *       Permission.UPDATE_COURSE_GRADE_SCHEMA)
    * post:
    *    gradeSchema' == g
    */
   public abstract void setGradeSchema(GradeSchema g);

   /**
    * Accessor fot the <code>LatePolicy</code> for this <code>Course</code>.
    * @return <code>LatePolicy</code> for this <code>Course</code>.
    * <p/>
    * <pre>
    * pre:
    *    session != null &&
    *    session.currentUser != null &&
    *    latePolicy != null &&
    *    roleManager.getPerms(session.currentUser).contains(
    *       Permission.ACCESS_COURSE_LATE_POLICY)
    * post:
    *    latePolicy' == latePolicy)
    */
   public abstract LatePolicy getLatePolicy();

   /**
    * Sets the <code>LatePolicy</code> for this <code>Course</code>.
    * @param l desired <code>LatePolicy</code>.
    * <pre>
    *  pre:
    *      session != null &&
    *      session.currentUser != null &&
    *      roleManager.getPerms(session.currentUser).contains(
    *      Permission.UPDATE_COURSE_LATE_POLICY)
    *  post:
    *      latePolicy' == l
    */
   public abstract void setLatePolicy(LatePolicy l);

   /**
    * Accessor fot the <code>StudentRecord</code>s for this <code>Course</code>.
    * @return <code>StudentRecord</code>s for this <code>Course</code>.
    * <p/>
    * <pre>
    * pre:
    *     studentRecords != null
    * post:
    *     studentRecords' == studentRecord
    */
   public abstract Collection<StudentRecord> getStudentRecords();

   /**
    * Accessor fot the <code>StudentRecord</code> for this <code>Course</code>.
    * @return <code>StudentRecord</code> for this <code>Course</code>.
    * <p/>
    * <pre>
    * pre:
    *     studentRecords != null && studentRecords.size() > 0
    * post:
    *     studentRecord' == studentRecord
    */
   public abstract StudentRecord getStudentRecord(User student);

   /**
    * Accessor fot the <code>Assignment</code>s for this <code>Course</code>.
    * @return all <code>Assignment</code>s from every
    * <code>AssignmentCategory</code> contained in this <code>Course</code>.
    * <p/>
    * <pre>
    * pre:
    *    session != null &&
    *    session.currentUser != null &&
    *    assignmentCategories != null &&
    *    roleManager.getPerms(session.currentUser).contains(
    *       Permission.ACCESS_ASSIGNMENT)
    * post:
    */
   public abstract Collection<Assignment> getAssignments();

   /**
    * Accessor fot the <code>AssignmentCategory</code>'s
    * for this <code>Course</code>.
    * <pre>
    * pre:
    *    session != null &&
    *    session.currentUser != null &&
    *    assignmentCategories != null &&
    *    roleManager.getPerms(session.currentUser).contains(Permission.ACCESS_ASSIGNMENT_CATEGORY)
    * post:
    *    assignmentCategories' == assignmentCategories
    * @return <code>StudentRecord</code> for this <code>Course</code>.
    */
   public abstract Collection<AssignmentCategory> getAssignmentCategories();

   /**
    * Accessor for the <code>AssignmentSubmission</code>s for this course.
    * @param assignment
    * @return
    * <pre>
    */
   public abstract Collection<AssignmentSubmission> getAssignmentSubmissions(
         Assignment assignment);

   /**
    * Accessor for the <code>AssignmentSubmission</code> of the given
    * <code>User</code> and <code>Assignment</code>.
    * @param assignment assignment associated with the submission to get.
    * @param student    student who submitted the submission.
    * @return <code>AssignmentSubmission</code>
    * for the specified assignment and the student.
    * <p/>
    * pre:
    *    session != null &&
    *    session.currentUser != null &&
    *    roleManager.getPerms(session.currentUser).contains(
    *    Permission.ACCESS_ASSIGNMENT_SUBMISSION)
    * post:
    */
   public abstract AssignmentSubmission getAssignmentSubmission(
         Assignment assignment, User student);

   /**
    * Accessor for all <code>AssignmentGrade</code>s for the specified
    * <code>Assignment</code>.
    * @param assignment assignment to access grades for.
    * @return All grades for the given assignment.
    * pre:
    *    session != null &&
    *    session.currentUser != null &&
    *    (roleManager.getPerms(session.currentUser).contains(
    *       Permission.ACCESS_ASSIGNMENT_GRADE)
    */
   public abstract Collection<AssignmentGrade> getAssignmentGrades(
         Assignment assignment);

   /**
    * Gets the grade the student earned on an assignment
    * @param assignment The assignment that you like to access the grade for
    * @param student    The student user to access a grade for
    * @return The grade received on the given assignment by the given user
    * <pre>
    * pre:
    *    session != null &&
    *    session.currentUser != null &&
    *    (roleManager.getPerms(session.currentUser).contains(
    *       Permission.ACCESS_ASSIGNMENT_GRADE) ||
    *    (session.currentUser.equals(student) &&
    *       roleManager.getRoles(session.currentUser).contains(Role.STUDENT))
    * post:
    *    // none yet
    */
   public abstract AssignmentGrade getAssignmentGrade(
         Assignment assignment, User student);

   /**
    * Creates a <code>Snapshot</code> of the course.
    * <p/>
    * <pre>
    * pre:
    *    session != null &&
    *    session.currentUser != null &&
    *    roleManager.getPerms(session.currentUser).contains(
    *       Permission.CREATE_COURSE_SNAPSHOT)
    * post:
    *    roleManage.equals(snapShot.roleManager) &&
    *    gradeSchema.equals(snapShot.gradeSchema) &&
    *    latePolicy.equals(snapShot.latePolicy) &&
    *    name.equals(snapShot.name) &&
    *    studentRecords.equals(snapShot.studentRecords) &&
    *    assignmentCategories.equals(snapShot.assignmentCategories) &&
    *
    *    roleManage'.equals(snapShot.roleManager) &&
    *    gradeSchema'.equals(snapShot.gradeSchema) &&
    *    latePolicy'.equals(snapShot.latePolicy) &&
    *    name'.equals(snapShot.name) &&
    *    studentRecords'.equals(snapShot.studentRecords) &&
    *    assignmentCategories'.equals(snapShot.assignmentCategories)
    */
   public abstract void createSnapshot();

   /**
    * Adds an assignment to the course
    * @param assignment The assignment to add to this course
    * <pre>
    * pre:
    *     session != null &&
    *     session.currentUser != null &&
    *     roleManager.getPerms(session.currentUser).contains(
    *        Permission.ADD_ASSIGNMENT)
    * post:
    */
   public abstract void addAssignment(Assignment assignment);

   /**
    * Updates an specified <code>Assignment</code>.
    * @param assignment The <code>Assignment</code> to update.
    * <pre>
    * pre:
    *     session != null &&
    *     session.currentUser != null &&
    *     roleManager.getPerms(session.currentUser).contains(
    *        Permission.UPDATE_ASSIGNMENT)
    * post:
    */
   public abstract void updateAssignment(Assignment assignment);

   /**
    * Adds an assignment category to the course
    * @param assignmentCategory The assignment category to add to this course
    * <pre>
    * pre:
    *    session != null &&
    *    session.currentUser != null &&
    *    roleManager.getPerms(session.currentUser).contains(
    *       Permission.ADD_ASSIGNMENT_CATEGORY)
    * post:
    */
   public abstract void addAssignmentCategory(AssignmentCategory assignmentCategory);

   public abstract void updateAssignmentCategory(AssignmentCategory assignmentCategory);

   public abstract void addAssignmentSubmission(AssignmentSubmission submission);

   public abstract void updateAssignmentSubmission(AssignmentSubmission submission);

   public abstract void updateAssignmentGrade(AssignmentGrade assignmentGrade);

   /**
    * Gets the current <code>AbstractCourseSnapshot</code>.
    * @return a snapshot for the current course
    * or null if no snapshot was created.
    * <p/>
    * <pre>
    * pre:
    *    session != null &&
    *    session.currentUser != null &&
    *    roleManager.getPerms(session.currentUser).contains(
    *       Permission.ACCESS_COURSE_SNAPSHOT)
    * post:
    *    snapShot == snapShot'
    */
   public abstract AbstractCourseSnapshot getSnapshot();

   /**
    * Adds a student to the student record this <code>AbstractCourse</code>
    * holds.
    * @param student student <code>User</code> to add to this course.
    * <p/>
    * <pre>
    * pre:
    *    session != null &&
    *    session.currentUser != null &&
    *    roleManager.getPerms(session.currentUser).contains(
    *       Permission.ADD_STUDENT)
    * post:
    *    studentRecords.size() == studentRecords'.size() - 1 &&
    *    exists (AbstractStudentRecord rec; rec.getStudentUserInfo(student))
    */
   public abstract void addStudent(User student);

   /**
    * Removes a student from the student record this <code>AbstractCourse</code>
    * holds.
    * @param student student <code>User</code> to remove from this course.
    * @return <p/>
    * <pre>
    * pre:
    *    session != null &&
    *    session.currentUser != null &&
    *    roleManager.getPerms(session.currentUser).contains(
    *       Permission.REMOVE_STUDENT)
    * post:
    *    studentRecords.size() == studentRecords'.size() + 1 &&
    *    !exists (AbstractStudentRecord rec; rec.getStudentUserInfo(student))
    */
   public abstract boolean removeStudent(User student);

}
