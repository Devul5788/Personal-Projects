import pytesseract
from pdf2image import convert_from_path
import tkinter as tk
from tkinter import filedialog
import openai
import questions

openai.api_key = "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"
MODEL = "gpt-3.5-turbo"

def select_file(file_type):
    # hide the root window of the tkinter GUI
    root = tk.Tk()
    root.withdraw()
    initial_dir = "/home/devul/GitHub/Personal-Projects/Startups/MarkMaster/Data"
    file_path = filedialog.askopenfilename(title=f"Select {file_type} PDF file", initialdir=initial_dir, filetypes=(("PDF files", "*.pdf"), ("All files", "*.*")))
    return file_path

def extract_text_from_pdf(pdf_path):
    images = convert_from_path(pdf_path)
    text = ""
    for image in images:
        text += pytesseract.image_to_string(image)
    return text

# Call the function to open the file dialog for the answer key & student answer file
answers_pdf = select_file("answer key")
student_answers_pdf = select_file("student answer")

# Extract text from answers PDFs
answers_text = extract_text_from_pdf(answers_pdf)
student_answers_text = extract_text_from_pdf(student_answers_pdf)

question_classifier_context = '''
You are a expert data extractor and classifier. Your job is to output each question, its classification, and its answer from the given text. 
The classification could be one of the following: FILL_IN_THE_BLANK, SHORT_ANSWER, MULTIPLE_CHOICE, TRUE_FALSE, ESSAY, DIAGRAM, OTHER.
Your answer should be in the following format: Question: ____ Type: ____ Answer: ____
'''

def askGPT(question, context, temp):
    response = openai.ChatCompletion.create(
        model=MODEL,
        messages=[
            {"role": "system", "content": context},
            {"role": "user", "content": question}
        ],
        temperature=temp,
    )
    return response.choices[0].message.content.strip()

preprocessed_QA_prompt = "Student Answers: " + student_answers_text + "Answer Key: " + answers_text
question_objects = questions.create_preprocessed_question_objects(askGPT(preprocessed_QA_prompt, question_classifier_context, 0))

for i in range(len(question_objects)):
    print(f"Question {i+1}: ")
    print(question_objects[i].get_question())
    print(question_objects[i].get_question_type())
    print(question_objects[i].get_answer())
    print()

