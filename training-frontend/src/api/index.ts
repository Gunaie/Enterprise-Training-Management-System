import axios from 'axios'
import type { LoginInfo, User, Course, Exam, Student, Question, CourseCategory, CourseEnroll, LearningProgress, ExamRecord, Result } from '../types'

const api = axios.create({
  baseURL: '/api',
  timeout: 10000
})

api.interceptors.request.use((config) => {
  const token = localStorage.getItem('token')
  if (token) {
    config.headers['token'] = token
  }
  return config
}, (error) => {
  return Promise.reject(error)
})

api.interceptors.response.use((response) => {
  if (response.data.code !== 200) {
    return Promise.reject(new Error(response.data.msg || '请求失败'))
  }
  return response.data
}, (error) => {
  if (error.response?.status === 401) {
    localStorage.removeItem('token')
    localStorage.removeItem('user')
    window.location.href = '/login'
  }
  return Promise.reject(error)
})

export const loginApi = (data: LoginInfo): Promise<Result<User>> => {
  return api.post('/login', data)
}

export const getCoursesApi = (): Promise<Result<Course[]>> => {
  return api.get('/courses')
}

export const getCourseByIdApi = (id: number): Promise<Result<Course>> => {
  return api.get(`/courses/${id}`)
}

export const createCourseApi = (data: Omit<Course, 'id' | 'createTime' | 'updateTime'>): Promise<Result<Course>> => {
  return api.post('/courses', data)
}

export const updateCourseApi = (id: number, data: Partial<Course>): Promise<Result<void>> => {
  return api.put(`/courses/${id}`, data)
}

export const deleteCourseApi = (id: number): Promise<Result<void>> => {
  return api.delete(`/courses/${id}`)
}

export const getCourseCategoriesApi = (): Promise<Result<CourseCategory[]>> => {
  return api.get('/course-categories')
}

export const getExamsApi = (): Promise<Result<Exam[]>> => {
  return api.get('/exams')
}

export const getExamByIdApi = (id: number): Promise<Result<Exam>> => {
  return api.get(`/exams/${id}`)
}

export const createExamApi = (data: Omit<Exam, 'id' | 'createTime'>): Promise<Result<Exam>> => {
  return api.post('/exams', data)
}

export const updateExamApi = (id: number, data: Partial<Exam>): Promise<Result<void>> => {
  return api.put(`/exams/${id}`, data)
}

export const deleteExamApi = (id: number): Promise<Result<void>> => {
  return api.delete(`/exams/${id}`)
}

export const getQuestionsApi = (examId: number): Promise<Result<Question[]>> => {
  return api.get(`/questions?examId=${examId}`)
}

export const createQuestionApi = (data: Omit<Question, 'id'>): Promise<Result<Question>> => {
  return api.post('/questions', data)
}

export const updateQuestionApi = (id: number, data: Partial<Question>): Promise<Result<void>> => {
  return api.put(`/questions/${id}`, data)
}

export const deleteQuestionApi = (id: number): Promise<Result<void>> => {
  return api.delete(`/questions/${id}`)
}

export const getStudentsApi = (): Promise<Result<Student[]>> => {
  return api.get('/students')
}

export const getStudentByIdApi = (id: number): Promise<Result<Student>> => {
  return api.get(`/students/${id}`)
}

export const createStudentApi = (data: Omit<Student, 'id' | 'createTime'>): Promise<Result<Student>> => {
  return api.post('/students', data)
}

export const updateStudentApi = (id: number, data: Partial<Student>): Promise<Result<void>> => {
  return api.put(`/students/${id}`, data)
}

export const deleteStudentApi = (id: number): Promise<Result<void>> => {
  return api.delete(`/students/${id}`)
}

export const getEnrollmentsApi = (): Promise<Result<CourseEnroll[]>> => {
  return api.get('/enrollments')
}

export const getMyEnrollmentsApi = (): Promise<Result<CourseEnroll[]>> => {
  return api.get('/enrollments/user/me')
}

export const enrollCourseApi = (courseId: number): Promise<Result<void>> => {
  return api.post('/enrollments', { courseId, userId: parseInt(localStorage.getItem('userId') || '0') })
}

export const updateProgressApi = (lessonId: number): Promise<Result<void>> => {
  return api.put(`/enrollments/progress?lessonId=${lessonId}`)
}

export const completeCourseApi = (courseId: number): Promise<Result<void>> => {
  return api.put(`/enrollments/complete/${courseId}`)
}

export const getLearningProgressApi = (studentId: number): Promise<Result<LearningProgress[]>> => {
  return api.get(`/learning-progress?studentId=${studentId}`)
}

export const getExamRecordsApi = (): Promise<Result<ExamRecord[]>> => {
  return api.get('/exam-records')
}

export const getMyExamRecordsApi = (): Promise<Result<ExamRecord[]>> => {
  return api.get('/exams/records/me')
}

export const submitExamApi = (examId: number, answers: string): Promise<Result<{ score: number; isPassed: boolean }>> => {
  return api.post(`/exams/${examId}/submit`, { answers })
}

export const getCurrentUserApi = (): Promise<Result<User>> => {
  return api.get('/students/me')
}

export const getStatisticsApi = (): Promise<Result<{
  totalCourses: number
  totalStudents: number
  totalExams: number
  totalEnrollments: number
}>> => {
  return api.get('/statistics')
}
