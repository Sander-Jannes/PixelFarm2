import os
import time

input_name = "rrr.png"
output_name = "./blocks/roads2.png"

os.system(f"tile_gen.py --i {input_name} --o {output_name} --background background.png")
print("ok!")
time.sleep(0.5)