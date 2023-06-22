from enum import Enum
import re

class QuestionType(Enum):
    FILL_IN_THE_BLANK = 1
    SHORT_ANSWER = 2
    MULTIPLE_CHOICE = 3
    TRUE_FALSE = 4
    ESSAY = 5
    DIAGRAM = 6
    OTHER = 7

class Question:
    def __init__(self, question, question_type, answer='', student_answer=''):
        self.question = question
        self.question_type = question_type
        self.answer = answer
        self.student_answer = student_answer

    def set_question(self, question):
        self.question = question

    def set_question_type(self, question_type):
        self.question_type = question_type

    def set_answer(self, answer):
        self.answer = answer

    def set_student_answer(self, student_answer):
        self.student_answer = student_answer

    def check_answer(self):
        if self.student_answer == self.answer:
            return True
        return False

    def get_question(self):
        return self.question

    def get_question_type(self):
        return self.question_type

    def get_answer(self):
        return self.answer

    def get_student_answer(self):
        return self.student_answer


def create_preprocessed_question_objects(text):
    questions = []
    
    # This pattern is used to extract information from a string containing a question, its type, and answer.
    # The pattern assumes a specific format where the question, type, and answer are separated by colons and newlines.
    # "([^:]+)" : Captures one or more characters that are not colons, representing the question text.
    pattern = r"Question: ([^:]+).*Type: ([^:]+).*Answer: ([^:]+)\n"

    # Returns a tuple (question, type, answer) that matches the pattern 
    matches = re.findall(pattern, text)
    
    for question, question_type, answer in matches:
        question_type = question_type.strip().upper().replace(" ", "_")
        question_type = QuestionType[question_type]
        questions.append(Question(question, question_type, answer))
    
    return questions
