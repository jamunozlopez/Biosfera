import os
import sys

def convert_file_encoding(file_path, output_path=None):
    """
    Convierte un archivo desde ISO-8859-1 a UTF-8.
    
    Args:
        file_path (str): Ruta al archivo a convertir
        output_path (str, opcional): Ruta donde guardar el archivo convertido. 
                                    Si no se proporciona, se sobrescribe el original.
    """
    if output_path is None:
        output_path = file_path + ".utf8"
    
    try:
        # Abre el archivo con la codificación ISO-8859-1
        with open(file_path, "r", encoding="ISO-8859-1") as file:
            content = file.read()
        
        # Escribe el contenido con codificación UTF-8
        with open(output_path, "w", encoding="UTF-8") as file:
            file.write(content)
        
        # Si queremos sobrescribir el archivo original
        if output_path != file_path + ".utf8":
            os.replace(output_path, file_path)
        else:
            print(f"Archivo convertido guardado como: {output_path}")
            
        print(f"Conversión exitosa: {file_path} de ISO-8859-1 a UTF-8")
    except Exception as e:
        print(f"Error al convertir {file_path}: {str(e)}")
        return False
    
    return True

if __name__ == "__main__":
    if len(sys.argv) > 1:
        # Si se proporciona un archivo como argumento
        input_file = sys.argv[1]
        if len(sys.argv) > 2:
            # Si se proporciona un archivo de salida
            output_file = sys.argv[2]
            convert_file_encoding(input_file, output_file)
        else:
            # Sobrescribir el archivo original
            convert_file_encoding(input_file, input_file)
    else:
        # Sin argumentos, buscar todos los archivos HTML en el directorio actual
        print("Convirtiendo archivos HTML en el directorio actual...")
        for root, dirs, files in os.walk('.'):
            for file in files:
                if file.endswith('.htm') or file.endswith('.html'):
                    file_path = os.path.join(root, file)
                    convert_file_encoding(file_path, file_path)