import openai
import os
import re

openai.api_key = "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"
MODEL = "gpt-3.5-turbo"

# Chat Contexts
prompt_evaluator_context = "Evaluate the quality of a question for writing an article. Reply with a rating (Excellent, Good, Fair, Poor) and provide an explanation. Make sure its in this format, Rating: ___ Explanation: ___"
prompt_engineer_context = "You are an expert prompt engineer for chatgpt. You take bad prompts and turn it to really good prompts."
interviewer_context = "Generate questions to ask the user for information to make the original questions far better.  You should reply with three follow-up questions that can help the user refine and improve their original question. Make the questions so that they are easy to answer for the user. Prompt:"
writer_context = "You are an expert writer. You can do anything requested by the prompt in a very a coherent and well written manner."

# Chats
def askGPT(question, context, temp):
    response = openai.ChatCompletion.create(
        model=MODEL,
        messages=[
            {"role": "system", "content": context},
            {"role": "user", "content": question}
        ],
        n=2,
        temperature=temp,
    )
    return response.choices[0].message.content.strip()

# A python program that takes user input for the following fields and stores it in a string format
# Define the fields and the possible options
fields = ["Topic", "Type", "Audience skill", "Author", "Length (words)", "Tone / writing style", "Any specific subtopics", "Goals", "Do you want the articles to use examples to explain", "Visual Elements", "Formatting Requirements", "Reference Materials", "Additional Remarks", "Citation Style"]
options = {"Type": ["Email", "Article", "Letter", "Detailed Notes"], 
            "Audience skill": ["novice", "competent", "expert"], 
            "Author": ["student", "teacher", "professional", "writer", "programmer"], 
            "Do you want the articles to use examples to explain": ["yes", "no"], 
            "Visual Elements": ["images", "videos", "graphs", "charts", "N/A"],
            "Citation Style": ["APA", "MLA", "Chicago", "Harvard", "N/A"]}
required = ["Topic", "Type", "Author", "Audience skill", "Length (words)", "Goals", "Do you want the articles to use examples to explain"]

# Initialize an empty dictionary to store the user input
user_input = {}

# Loop through the fields and ask for user input
for field in fields:
    print(f"{field}", end="") # end="" prevents newline

    if field in required:
        print("* : ", end="") # print asterisk and colon
    else:
        print(" : ", end="") # print only colon

    # Check if the field has predefined options
    if field in options:
        print()
        # Print the options and ask the user to select a number
        for i, option in enumerate(options[field]):
            print(f"{i+1}. {option}")
        
        # Validate the user input
        while True:
            try:
                choice = int(input("Enter your choice: "))
                if choice < 1 or choice > len(options[field]):
                    raise ValueError
                break
            except ValueError:
                print("Invalid input. Please enter a number between 1 and " + str(len(options[field])))
        # Store the user input in the dictionary
        user_input[field] = options[field][choice-1]
    else:
        # Ask the user to enter a value for the field until they do so if it is required
        while True:
            value = input()
            # Check if the field is required and the value is not empty
            if field in required and value == "":
                print(f"{field} is required. Please enter a value.")
            else:
                break
        
        # Store the user input in the dictionary
        user_input[field] = value

# Format the user input into a string
options = ""
for field in fields:
    # Check if the user input is not empty
    if user_input[field] != "" and user_input[field] != "N/A":
        # Add the field and the user input to the output string
        options += f"{field}: {user_input[field]}\n"

# Print the output string
print("\nDetails Selected:")
print(options)

questions = askGPT(options, interviewer_context, 0.8)

pattern = r"(?m)^(.*\?)$"
matches = re.findall(pattern, questions)
question_array = list(matches)

answers = []

print("Please answer these questions to increase the quality of the response.")

for question in question_array:
    user_answer = input(question + " ")
    while not user_answer:
        print("Please provide an answer.")
        user_answer = input(question + " ")
    answers.append(user_answer)

added_info = ""

for i in range(len(question_array)):
    added_info += question_array[i] + " Answer: " + answers[i] + "\n"

better_prompt = "Create a much better prompt to ask chatgpt then this: Use the following information generate a prompt that encapsulates ALL of the following formation in simple prompt. Keep the prompt concise, make sure you empahsize the TYPE field more. This is the information: "

improved_prompts = []
improved_prompts.append(askGPT(better_prompt + options + added_info, prompt_engineer_context, 0))
improved_prompts.append(askGPT(better_prompt + options + added_info, prompt_engineer_context, 0.5))
improved_prompts.append(askGPT(better_prompt + options + added_info, prompt_engineer_context, 1))

print("\n Choose one of the prompts based on all the details you have provided. ")
for i, option in enumerate(improved_prompts):
    print(f"{i+1}. {option}\n")

while True:
    try:
        choice = int(input("Enter your choice: "))
        if choice < 1 or choice > len(improved_prompts):
            raise ValueError
        break
    except ValueError:
        print("Invalid input. Please enter a number between 1 and " + str(len(improved_prompts)))

prompt_chosen = improved_prompts[choice - 1]

print("chosen prompt: " + prompt_chosen)

def extract_ratings_exp(content):
    rating_pattern = r"Rating: (Excellent|Good|Fair|Poor)"
    explanation_pattern = r"Explanation: (.*)"
    rating_match = re.search(rating_pattern, content)
    explanation_match = re.search(explanation_pattern, content)
    rating = rating_match.group(1).strip() if rating_match else "Invalid"
    explanation = explanation_match.group(1).strip() if explanation_match else "Invalid"
    return rating, explanation

rating, explanation = extract_ratings_exp(askGPT(prompt_chosen, prompt_evaluator_context, 0.8)) 
print("Rating:", rating)
print("Explanation:", explanation)

essay = askGPT(prompt_chosen, writer_context, 0.8)

print("Essay: \n" + essay)